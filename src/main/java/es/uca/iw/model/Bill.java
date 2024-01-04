package es.uca.iw.model;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    private LocalDate date;
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) {
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

    @NotNull(message = "El precio es obligatorio")
    private BigDecimal callTotalPrice;
    public BigDecimal getCallTotalPrice() { return callTotalPrice; }

    @NotNull(message = "El precio es obligatorio")
    private BigDecimal totalPrice;
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) {
        if(totalPrice.compareTo(new BigDecimal(0)) < 0)
            throw new IllegalArgumentException("El precio no puede ser negativo");
        this.totalPrice = totalPrice;
    }

    @ManyToOne
    @NotNull(message = "El contrato es obligatorio")
    Contract contract;
    public Contract getContract() { return contract; }
    public void setContract(Contract contract) {
        this.contract = contract;
    }

    @PrePersist
    public void prePersist() {
        totalPrice = contract.getProduct().getPrice();

        dataTotalPrice = new BigDecimal(0);
        if (dataConsumed - contract.getProduct().getDataUsageLimit() > 0) {
            dataTotalPrice = new BigDecimal(contract.getProduct().getDataUsageLimit()).multiply(contract.getProduct().getDataPenaltyPrice());
            totalPrice = totalPrice.add(dataTotalPrice);
        }

        callTotalPrice = new BigDecimal(0);
        if (minutesConsumed - contract.getProduct().getCallLimit() > 0) {
            callTotalPrice = new BigDecimal(contract.getProduct().getCallLimit()).multiply(contract.getProduct().getCallPenaltyPrice());
            totalPrice = totalPrice.add(callTotalPrice);
        }
    }
}
