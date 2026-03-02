// This handles synchronization of bills into the local database. Data is sent here from CongressService

package local.fox.fenestra.service;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import tools.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

import local.fox.fenestra.mapper.BillMapper;
import local.fox.fenestra.repository.BillRepository;
import local.fox.fenestra.entity.Bill;


@Service
public class BillSyncService {
    // private static final Logger log = LoggerFactory.getLogger(BillSyncService.class);

    private final BillMapper billMapper;
    private final BillRepository billRepository;

    public BillSyncService(
        BillMapper billMapper,
        BillRepository billRepository
    ){
        this.billMapper = billMapper;
        this.billRepository = billRepository;
    }

    public int syncPage(JsonNode billsJson) {
        int updatedBills=0;
        JsonNode billArray = billsJson.path("bills");

        List<Bill> billList = new ArrayList<>();

        for (JsonNode json : billArray) {
            Bill bill = billMapper.fromCongressApiJson(json);
            if (!billRepository.existsById(bill.getId())) {
                billList.add(bill);
            }
            // TODO: Add logic to compare bill update time against database and update existing records if necessary.
        }

        if (!billList.isEmpty()) {
            billRepository.saveAll(billList);
            updatedBills = billList.size();
        }

        return updatedBills;
    }
}
