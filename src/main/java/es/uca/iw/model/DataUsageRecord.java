package es.uca.iw.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class DataUsageRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    public Long getApiId() { return Id; }
    public void setApiId(Long Id) { this.Id = Id; }

    @NotNull
    Integer megaBytes;
    public Integer getMegaBytes() { return megaBytes; }
    public void setMegaBytes(Integer megaBytes) { this.megaBytes = megaBytes; }

    @NotNull
    Date date;
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    @NotNull
    @ManyToOne
    private Contract contract;
    public Contract getContract() { return contract; }
    public void setContract(Contract contract) {
        if (contract == null)
            throw new IllegalArgumentException("El contrato no puede ser nulo");
        this.contract = contract;
    }
}
