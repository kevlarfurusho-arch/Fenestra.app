// This is the service dedicated to handling operations. This speaks to the internal services and determines if it needs to call the CongressApiService to get data from the congress API.

package local.fox.fenestra.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tools.jackson.databind.JsonNode;

import java.util.Optional;

import local.fox.fenestra.entity.Bill;
import local.fox.fenestra.entity.keys.BillId;
import local.fox.fenestra.mapper.BillMapper;
import local.fox.fenestra.repository.BillRepository;
import local.fox.fenestra.dto.BillSummary;

@Service

public class CongressService {
    private static final Logger log = LoggerFactory.getLogger(CongressService.class);

    private final CongressApiService congressApiService;
    private final BillSyncService billSyncService;
    private final BillMapper billMapper;
    private final BillRepository billRepository;

    public CongressService (
        CongressApiService congressApiService,
        BillMapper billMapper,
        BillPersistenceService billPersistenceService,
        BillRepository billRepository,
        BillSyncService billSyncService
    ) {
        this.billSyncService = billSyncService;
        this.congressApiService = congressApiService;
        this.billMapper = billMapper;
        this.billRepository = billRepository;
    }

    // Retrieve data for a specific bill from cache, database, or congress.gov API
    public Bill getBill(int billNumber, String billType, int congress) {
        BillId id = new BillId(congress, billType.toUpperCase(), billNumber);

        log.info("Retrieving bill ID: " + id);
        // System.out.println("Bill ID set to: " + id.getCongress() + id.getBillType() + id.getBillNumber());
        /* TODO:
        1. Check redis cache for the bill data
        2. If found, return cached data
        3. If not found, check local database for the bill data
        4. If found in database, cache it in redis and return it
        */

        // 1. Check Redis cache for the bill data
        /* THIS IS FOR THE REDIS CACHE IMPLEMENTATION EVENTUALLY
        System.out.println("Checking redis");
        boolean cache = false; // Placeholder for cache retrieval
        if (cache) { // Placeholder condition
            Bill cachedBill = null;
            return cachedBill; // Placeholder return
        } else {
            System.out.println("Redis miss")
        }

        */

        // 2. Check the database for the bill
        Optional<Bill> dbBill = billRepository.findById(id);
        System.err.println(dbBill.toString());
        if (dbBill.isPresent()) {
            log.debug("db Hit.");
            return dbBill.get();
        } else {
            log.debug("db Miss");
        }

        // 3. Fetch from congress.gov API
        log.info("Fetching bill from congress.gov API");
        JsonNode json = congressApiService.fetchBillRaw(congress, billType, billNumber);

        if (json == null || json.isEmpty()) {
            throw new RuntimeException("Failed to fetch bill data from congress.gov API");
        }
        log.debug("API Hit");

        // 4. map JSON to Bill entity
        Bill bill = billMapper.fromCongressApiJson(json);
        return (bill);

        // 5. Persist to database
        // return billPersistenceService.saveBill(bill);
        /* TODO:
        1. Parse JSON
        2. Persist to database
        3. Cache in Redis
        */
    }

    // Retrieve all current bills and pass to BillSyncService for processing
    public void syncBills(int congress) {
        log.info("Syncing Bills");

        int offset = 0;
        int limit = 250;
        int totalcount = 0;
        int updatedBills = 0;
        
        do {
            log.debug("Fetching bills {} to {} of {}", offset, offset+limit, totalcount);
            
            JsonNode pageJson = congressApiService.fetchBillsPage(congress, offset, limit);

            if (pageJson == null) {
                log.warn("Stopping sync due to API failure");
                break;
            }

            if (offset == 0) {
                totalcount = pageJson.path("pagination").path("count").asInt();
                if (totalcount == 0) {
                    return;
                }
            }

            offset += limit;

            int updated = billSyncService.syncPage(pageJson);
            updatedBills += updated;
        } while (offset < totalcount);

        log.info("Finished Sync for congress {}. Total bills updated: {}", congress, updatedBills);
        /* 
        // int totalCount = Integer.MAX_VALUE;

        JsonNode countCheck = congressApiService.fetchBillsPage(congress, 0, 0);
        int totalCount = countCheck.path("pagination").path("count").asInt(); // Placeholder for total count - Gets set to count after pulling first page

        while (offset < totalCount) {

            JsonNode pageJson = congressApiService.fetchBillsPage(congress, offset, limit);

            if (pageJson == null) {
                throw new RuntimeException("Failed to fetch bills page");
            }
            ////
            if (totalCount == Integer.MAX_VALUE) {
                totalCount = pageJson.path("pagination").path("count").asInt();
            }
            ////

            billSyncService.syncPage(pageJson);
            
            offset += limit;
        }
        */
    }

    public Page<BillSummary> getHouseBills(Pageable pageable) {
        return billRepository.findByOriginChamberCode("H", pageable)
            .map(b -> new BillSummary(
                b.getId().getCongress(),
                b.getId().getBillType(),
                b.getId().getBillNumber(),
                b.getTitle()
            ));
    }

    public Page<BillSummary> getSenateBills(Pageable pageable) {
        return billRepository.findByOriginChamberCode("S", pageable)
            .map(b -> new BillSummary(
                b.getId().getCongress(),
                b.getId().getBillType(),
                b.getId().getBillNumber(),
                b.getTitle()
            ));
    }
}
