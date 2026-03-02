package local.fox.fenestra.dto;

public record BillSummary (
    int congress,
    String billType,
    int billNumber,
    String title
) {}