package local.fox.fenestra.dto;

import java.time.LocalDate;

public record BillSummary (
    int congress,
    String billType,
    int billNumber,
    String title,
    LocalDate updateDate
) {}