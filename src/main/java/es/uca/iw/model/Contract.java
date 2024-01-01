package es.uca.iw.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import java.util.Calendar;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GenerationType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Contract {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    public Long getId() { return id; }

    @NotNull(message = "Incluya la fecha de inicio, por favor")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) {  // Deberiamos de hacer mas comprobaciones
        if (startDate == null)
            throw new IllegalArgumentException("La fecha de inicio no puede ser nula");
        this.startDate = startDate;
    }

    @NotNull(message = "Incluya la fecha de fin, por favor")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) {  // Deberiamos de hacer mas comprobaciones
        if (endDate == null)
            throw new IllegalArgumentException("La fecha de fin no puede ser nula");
        this.endDate = endDate;
    }

    private UUID apiId;
    public UUID getApiId() { return apiId; }
    public void setApiId(UUID apiId) { this.apiId = apiId; }

    @NotNull
    @ManyToOne
    private User user;
    public User getUser() { return user; }
    public void setUser(User user) {
        if (user == null)
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        this.user = user;
    }

    @NotNull
    @ManyToOne
    private Product product;
    public Product getProduct() { return product; }
    public void setProduct(Product product) {
        if (product == null)
            throw new IllegalArgumentException("El producto no puede ser nulo");
        this.product = product;
    }

    @NotNull
    @NotEmpty(message = "Incluya el nombre de la línea, por favor")
    private String phoneNumber;
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private Date lastCallDataUpdate = null;
    public Date getLastCallDataUpdate() { return lastCallDataUpdate; }
    public void setLastCallDataUpdate(Date lastCallDataUpdate) {
        if (lastCallDataUpdate == null)
            throw new IllegalArgumentException("La fecha de la última actualización de llamadas no puede ser nula");
        this.lastCallDataUpdate = lastCallDataUpdate;
    }

    private Date lastBillUpdate = null;
    public Date getLastBillUpdate() { return lastBillUpdate; }
    public void setLastBillUpdate(Date lastBillUpdate) {
        if (lastBillUpdate == null)
            throw new IllegalArgumentException("La fecha de la última actualización de facturas no puede ser nula");
        this.lastBillUpdate = lastBillUpdate;
    }

    @OneToMany(mappedBy = "contract", fetch = FetchType.EAGER)
    private Set<DataUsageRecord> dataUsageRecord = new HashSet<>();
    public Set<DataUsageRecord> getDataUsageRecords() { return dataUsageRecord; }
    public void setDataUsgaeRecords(Set<DataUsageRecord> dataUsageRecord) {
        this.dataUsageRecord = dataUsageRecord;
    }

    @OneToMany(mappedBy = "contract", fetch = FetchType.EAGER)
    private Set<CallRecord> callRecord;
    public Set<CallRecord> getCallRecord() { return callRecord; }
    public void setCallRecord(Set<CallRecord> callRecord) {
        this.callRecord = callRecord;
    }

    @OneToMany(mappedBy = "contract", fetch = FetchType.EAGER)
    private Set<Bill> bills;
    public Set<Bill> getBills() { return bills; }
    public void setBills(Set<Bill> bills) {
        this.bills = bills;
    }

    @PrePersist
    public void PrePersist() {
        // Actual date (milisenconds)
        startDate = new Date(Calendar.getInstance().getTime().getTime());

        // Add 1 year to the current date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.YEAR, 1);
        endDate = new Date(calendar.getTime().getTime());
    }
}