// This is the repository interface for the Bill entity

package local.fox.fenestra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import local.fox.fenestra.entity.Bill;
import local.fox.fenestra.entity.keys.BillId;

@Repository
public interface BillRepository extends JpaRepository<Bill, BillId> {
    boolean existsByIdCongressAndIdBillTypeAndIdBillNumber(
        int congress,
        int billNumber,
        String billType        
    );

    Page<Bill> findByOriginChamberCode(String originChamberCode, Pageable pageable);
}