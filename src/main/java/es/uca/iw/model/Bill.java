package es.uca.iw.model;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;

@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Long getId() { return id; }
    
    @NotNull(message = "La fecha es obligatoria")
    private Date date;
    public Date getDate() { return date; }
    public void setDate(Date date) {
        this.date = date;
    }

    @NotNull(message = "La cantidad de datos consumidos es obligatoria")
    private Integer dataConsumed;
    public Integer getdataConsumed() { return dataConsumed; }
    public void setdataConsumed(Integer dataConsumed) {
        this.dataConsumed = dataConsumed;
    }

    @NotNull(message = "El precio de los datos consumidos es obligatorio")
    private BigDecimal dataPrice;
    public BigDecimal getdataPrice() { return dataPrice; }
    public void setdataPrice(BigDecimal dataPrice) {
        this.dataPrice = dataPrice;
    }

    @NotNull(message = "La cantidad de minutos consumidos es obligatoria")
    private Integer minutesConsumed;
    public Integer getminutesConsumed() { return minutesConsumed; }
    public void setminutesConsumed(Integer minutesConsumed) {
        this.minutesConsumed = minutesConsumed;
    }

    @NotNull(message = "El precio es obligatorio")
    private BigDecimal minutesPrice;
    public BigDecimal getminutesPrice() { return minutesPrice; }
    public void setminutesPrice(BigDecimal minutesPrice) {
        this.minutesPrice = minutesPrice;
    }

    @NotNull(message = "El precio es obligatorio")
    private BigDecimal totalPrice;
    public BigDecimal gettotalPrice() { return totalPrice; }

    @ManyToOne
    @NotNull(message = "El contrato es obligatorio")
    Contract contract;
    public Contract getContract() { return contract; }
    public void setContract(Contract contract) {
        this.contract = contract;
    }

    @PrePersist
    public void prePersist() {
        BigDecimal totalDataPrice;

        if (dataConsumed - contract.getProduct().getDataUsageLimit() <= 0) {
           totalDataPrice = dataPrice.multiply(new BigDecimal(dataConsumed));
        } else {
            totalDataPrice = dataPrice.multiply(new BigDecimal(dataConsumed - contract.getProduct().getDataUsageLimit()));
            totalDataPrice = totalDataPrice.add(new BigDecimal(contract.getProduct().getDataUsageLimit()).multiply(contract.getProduct().getDataPenaltyPrice()));
        }
        
        this.totalPrice = totalDataPrice;
    }
}
