package es.uca.iw.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


@Entity
public class CallRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    public Long getApiId() { return Id; }
    public void setApiId(Long Id) { this.Id = Id; }

    @NotNull
    @NotEmpty(message = "Incluya el número de teléfono de telefono de destino, por favor")
    private String destinationPhoneNumber;
    public String getDetinationPhoneNumber() { return destinationPhoneNumber; }
    public void setDetinationPhoneNumber(String destinationPhoneNumber) {
        this.destinationPhoneNumber = destinationPhoneNumber;
    }

    @NotNull
    private Integer seconds;
    public Integer getSeconds() { return seconds; }
    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    @NotNull
    private LocalDateTime dateTime;
    public LocalDateTime getDate() { return dateTime; }
    public void setDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }    

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
