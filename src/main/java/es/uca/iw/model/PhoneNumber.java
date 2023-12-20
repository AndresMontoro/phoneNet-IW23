package es.uca.iw.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;
    public Long getId() { return id; }

    @NotNull
    @NotEmpty(message = "Incluya el número de teléfono, por favor")
    String number;
    public String getNumber() { return number; }
    public void setNumber(String number) {
        if (number.length() != 9)
            throw new IllegalArgumentException("Formato del número de teléfono incorrecto");
        this.number = number;
    }

    @NotNull
    Boolean available = true;
    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }
}
