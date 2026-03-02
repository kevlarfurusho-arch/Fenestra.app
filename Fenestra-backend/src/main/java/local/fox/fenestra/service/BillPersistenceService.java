package local.fox.fenestra.service;

import org.springframework.stereotype.Service;
import java.util.List;

import local.fox.fenestra.entity.Bill;
import local.fox.fenestra.repository.BillRepository;

@Service
public class BillPersistenceService {

    private final BillRepository billRepository;

    public BillPersistenceService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public Bill saveBill(Bill bill) {
        return billRepository.save(bill);
    }

    public List<Bill> saveBills(List<Bill> bills) {
        return billRepository.saveAll(bills);
    }
}
