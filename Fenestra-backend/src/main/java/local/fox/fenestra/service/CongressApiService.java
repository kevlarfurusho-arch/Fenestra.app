// This is the service dedicated to handling communication with the congress.gov API

package local.fox.fenestra.service;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.web.client.RestTemplate;

import tools.jackson.databind.JsonNode;

@Service
public class CongressApiService {

    @Value("${congress.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    // private static final Logger log = LoggerFactory.getLogger(CongressApiService.class);

    // This fetches the raw JSON data for a specific bill
    public JsonNode fetchBillRaw(int congress, String billType, int billNumber) {
        String url = String.format("https://api.congress.gov/v3/bill/%s/%s/%s?api_key=%s&format=json", congress, billType, billNumber, apiKey);
        return restTemplate.getForObject(url, JsonNode.class);
    }

    // this fetches all of the bills for a specific congress session. Retry up to 5 times, 10 seconds apart.
    @Retryable(
        includes = Exception.class,
        maxRetries = 5,
        delay = 10000
    )
    public JsonNode fetchBillsPage(int congress, int offset, int limit) {
        String url = String.format("https://api.congress.gov/v3/bill/%s?offset=%d&limit=%d&api_key=%s&format=json", congress, offset, limit, apiKey);
        return restTemplate.getForObject(url, JsonNode.class);
    }
}