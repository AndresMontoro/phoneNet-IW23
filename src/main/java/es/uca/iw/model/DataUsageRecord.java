package es.uca.iw.model;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class DataUsageRecord {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    public Long getId() { return id; }

    @NotNull
    private UUID apiId;
    public UUID getApiId() { return apiId; }
    public void setApiId(UUID apiId) { this.apiId = apiId; }

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
