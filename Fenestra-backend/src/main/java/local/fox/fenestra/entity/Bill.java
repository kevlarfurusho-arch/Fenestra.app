// This is the JPA entity class representing a legislative bill

package local.fox.fenestra.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import local.fox.fenestra.entity.keys.BillId;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Entity
@Table(name = "bills")
public class Bill {
    // entity field-to-database column mappings
    @EmbeddedId
    private BillId id;

    @Column(nullable = false)
    private String title;

    @Column(name = "origin_chamber")
    private String originChamber;

    @Column(name = "origin_chamber_code")
    private String originChamberCode;

    @Column(name = "latest_action_date")
    private LocalDate latestActionDate;

    @Column(name = "latest_action_text")
    private String latestActionText;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "api_url")
    private String apiUrl;

    @Column(name = "last_sync", nullable = false)
    private ZonedDateTime lastSync;

    // getters and setters
    public BillId getId() { return id; }
    public void setId(BillId id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getOriginChamber() { return originChamber; }
    public void setOriginChamber(String originChamber) { this.originChamber = originChamber; }

    public String getOriginChamberCode() { return originChamberCode; }
    public void setOriginChamberCode(String originChamberCode) { this.originChamberCode = originChamberCode; }

    public LocalDate getLatestActionDate() { return latestActionDate; }
    public void setLatestActionDate(LocalDate latestActionDate) { this.latestActionDate = latestActionDate; }

    public String getLatestActionText() { return latestActionText; }
    public void setLatestActionText(String latestActionText) { this.latestActionText = latestActionText; }

    public LocalDate getUpdateDate() { return updateDate; }
    public void setUpdateDate(LocalDate updateDate) { this.updateDate = updateDate; }

    public String getApiUrl() { return apiUrl; }
    public void setApiUrl(String apiUrl) { this.apiUrl = apiUrl; }

    public ZonedDateTime getLastSync() { return lastSync; }
    public void setLastSync(ZonedDateTime lastSync) { this.lastSync = lastSync; }

    // Default constructor for JPA
    protected Bill() {}

    // Parameterized constructor
    public Bill(BillId id, String title) {
        this.id = id;
        this.title = title;
        this.lastSync = ZonedDateTime.now(ZoneOffset.UTC);
    }

    // Update bill fields during synchronization
    public void syncUpdate(
        String originChamber,
        String originChamberCode,
        LocalDate latestActionDate,
        String latestActionText,
        LocalDate updateDate,
        String apiUrl
    ) {
        this.originChamber = originChamber;
        this.originChamberCode = originChamberCode;
        this.latestActionDate = latestActionDate;
        this.latestActionText = latestActionText;
        this.updateDate = updateDate;
        this.apiUrl = apiUrl;
        this.lastSync = ZonedDateTime.now(ZoneOffset.UTC);
    }
}
