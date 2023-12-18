package es.uca.iw.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


@Entity
public class CallRecord {
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    public Long getId() { return id; }

    @NotNull
    @NotEmpty(message = "Incluya el número de teléfono de origen, por favor")
    private String destinationPhoneNumber;
    public String getDetinationPhoneNumber() { return destinationPhoneNumber; }
    public void setDetinationPhoneNumber(String destinationPhoneNumber) {
        if (destinationPhoneNumber == null || destinationPhoneNumber.isEmpty() || destinationPhoneNumber.length() != 9)
            throw new IllegalArgumentException("Formato del número de teléfono incorrecto");
        this.destinationPhoneNumber = destinationPhoneNumber;
    }

    @NotNull
    private Integer seconds;
    public Integer getSeconds() { return seconds; }
    public void setSeconds(Integer seconds) {
        if (seconds == null || seconds < 0)
            throw new IllegalArgumentException("Los segundos ser negativos");
        this.seconds = seconds;
    }

    @NotNull
    @NotEmpty(message = "Incluya la fecha, por favor")
    private LocalDateTime date;
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @NotNull
    private UUID apiId;
    public UUID getApiId() { return apiId; }
    public void setApiId(UUID apiId) { this.apiId = apiId; }
}
