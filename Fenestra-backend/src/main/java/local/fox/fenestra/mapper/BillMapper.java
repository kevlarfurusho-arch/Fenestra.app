// This is the mapper class to convert JSON data from the congress.gov API into Bill entities

package local.fox.fenestra.mapper;

import org.springframework.stereotype.Component;

import tools.jackson.databind.JsonNode;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import local.fox.fenestra.entity.Bill;
import local.fox.fenestra.entity.keys.BillId;

@Component
public class BillMapper {

    public Bill fromCongressApiJson(JsonNode json) {
        
        BillId id = new BillId(
            json.path("congress").asInt(),
            json.path("type").asString().toUpperCase(),
            json.path("number").asInt()
        );
        
        String title = json.path("title").asString();
        String originChamber = json.path("originChamber").asString(null);
        String originChamberCode = json.path("originChamberCode").asString(null);
        String latestActionText = json.path("latestAction").path("text").asString(null);
        String apiUrl = json.path("url").asString();

        String updateDateStr = json.path("updateDate").asString(null);
        String actionDateStr = json.path("latestAction").path("actionDate").asString(null);
        
        LocalDate updateDate = null;
        if (updateDateStr != null && !updateDateStr.isEmpty()) {
            updateDate = LocalDate.parse(updateDateStr);
        }
        
        LocalDate latestActionDate = null;
        if (actionDateStr != null && !actionDateStr.isEmpty()) {
            latestActionDate = LocalDate.parse(actionDateStr);
        }
        
        ZonedDateTime lastSync = ZonedDateTime.now(ZoneOffset.UTC);
        
        // Set the bill values
        Bill bill = new Bill(id, title);
        bill.setOriginChamber(originChamber);
        bill.setOriginChamberCode(originChamberCode);
        bill.setLatestActionDate(latestActionDate);
        bill.setLatestActionText(latestActionText);
        bill.setUpdateDate(updateDate);
        bill.setApiUrl(apiUrl);
        bill.setLastSync(lastSync);

        return bill;
    }
}