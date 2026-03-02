// This is the REST controller to handle requests to be passed to the congress.gov API service

package local.fox.fenestra.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;

import local.fox.fenestra.service.CongressService;
import local.fox.fenestra.entity.Bill;
import local.fox.fenestra.dto.BillSummary;

@RestController
@RequestMapping("/api/congress")
public class CongressController {
    
    private final CongressService congressService;
    private final PagedResourcesAssembler<BillSummary> assembler;

    public CongressController(
        CongressService congressService,
        PagedResourcesAssembler<BillSummary> assembler
    ) {
        this.congressService = congressService;
        this.assembler = assembler;
    }

    @GetMapping("/sync/{congress}")
    public void syncBills(@PathVariable int congress) {
        congressService.syncBills(congress);
    }

    @GetMapping("/bill/{congress}/{billType}/{billNumber}")
    public Bill fetchBill(
        @PathVariable int congress,
        @PathVariable String billType,
        @PathVariable int billNumber
    ) {
        return congressService.getBill(billNumber, billType, congress);
    }

    @GetMapping("/test/{congress}/{billType}/{billNumber}")
    public Bill testApi(
        @PathVariable int congress,
        @PathVariable String billType,
        @PathVariable int billNumber
    ) {
        return congressService.getBill(congress, billType, billNumber);
    }

    @GetMapping("/bill/house")
    public PagedModel<EntityModel<BillSummary>> getHouseBills(Pageable pageable) {
        Page<BillSummary> page = congressService.getHouseBills(pageable);
        return assembler.toModel(page);
    }

    @GetMapping("/bill/senate")
    public PagedModel<EntityModel<BillSummary>> getSenateBills(Pageable pageable) {
        Page<BillSummary> page = congressService.getSenateBills(pageable);
        return assembler.toModel(page);
    }
}
