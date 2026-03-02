// This is the composite key class for the Bill entity

package local.fox.fenestra.entity.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BillId implements Serializable {
    
    @Column(name = "congress", nullable = false)
    private int congress;

    @Column(name = "bill_type", nullable = false)
    private String billType;

    @Column(name = "bill_number", nullable = false)
    private int billNumber;

    // Default constructor for JPA
    protected BillId() {}
    
    // Parameterized constructor
    public BillId(int congress, String billType, int billNumber) {
        this.congress = congress;
        this.billType = billType;
        this.billNumber = billNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BillId)) return false;
        BillId billId = (BillId) o;
        return congress == billId.congress &&
               billNumber == billId.billNumber &&
               Objects.equals(billType, billId.billType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(congress, billType, billNumber);
    }

    // Getters and setters

    public int getCongress() { return congress; }
    public void setCongress(int congress) { this.congress = congress; }

    public String getBillType() { return billType; }
    public void setBillType(String billType) { this.billType = billType; }

    public int getBillNumber() { return billNumber; }
    public void setBillNumber(int billNumber) { this.billNumber = billNumber; }
}
