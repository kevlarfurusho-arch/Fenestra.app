package local.fox.fenestra.controller;

import local.fox.fenestra.service.CongressService;
import local.fox.fenestra.entity.Bill;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    public final CongressService congressService;
    public ApiController(CongressService congressService) {
        this.congressService = congressService;
    }

    @GetMapping("/ping") // Separate to separate health controller later. Use to track site up/down status and testing API connection from frontend.
    public String ping() {
        return "pong";
    }

    @GetMapping("/tester")
    public Bill tester() {
        return congressService.getBill(119, "hr", 234);
    }

    @GetMapping("/PageTest")
    public void pageTest() {
        // return BillSyncService.syncBillsPage(119);
    }
}