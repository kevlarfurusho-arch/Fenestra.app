package local.fox.fenestra.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import local.fox.fenestra.component.AppDataComponent;
import local.fox.fenestra.dto.AppDataDTO;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/appdata")
public class AppDataController {
    
    private final AppDataComponent appDataComponent;
    
    public AppDataController(AppDataComponent appDataComponent) {
        this.appDataComponent = appDataComponent;
    }

    @GetMapping
    public AppDataDTO getAppData() {
        return new AppDataDTO(
            appDataComponent.getName(), 
            appDataComponent.getVersion()
        );
    }
}
