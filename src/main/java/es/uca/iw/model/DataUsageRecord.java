package es.uca.iw.model;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class DataUsageRecord {

    @Id
    private UUID Id;
    public UUID getApiId() { return Id; }
    public void setApiId(UUID Id) { this.Id = Id; }

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
}
