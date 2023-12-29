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

    @NotNull(message = "La cantidad de minutos consumidos es obligatoria")
    private Integer minutesConsumed;
    public Integer getminutesConsumed() { return minutesConsumed; }
    public void setminutesConsumed(Integer minutesConsumed) {
        this.minutesConsumed = minutesConsumed;
    }

    @NotNull(message = "El precio de los datos es obligatorio")
    private BigDecimal dataTotalPrice;
    public BigDecimal getDataTotalPrice() { return dataTotalPrice; }

    // @NotNull(message = "El precio es obligatorio")
    // private BigDecimal callTotalPrice;
    // public BigDecimal getCallTotalPrice() { return callTotalPrice; }

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
        dataTotalPrice = new BigDecimal(0);

        if (dataConsumed - contract.getProduct().getDataUsageLimit() <= 0) {
           dataTotalPrice = contract.getProduct().getPrice();
        } else {
            dataTotalPrice = contract.getProduct().getDataUsagePrice().multiply(new BigDecimal(dataConsumed - contract.getProduct().getDataUsageLimit()));
            dataTotalPrice = dataTotalPrice.add(new BigDecimal(contract.getProduct().getDataUsageLimit()).multiply(contract.getProduct().getDataPenaltyPrice()));
            dataTotalPrice = dataTotalPrice.add(contract.getProduct().getPrice());
        }
        
        this.totalPrice = dataTotalPrice;
    }
}
