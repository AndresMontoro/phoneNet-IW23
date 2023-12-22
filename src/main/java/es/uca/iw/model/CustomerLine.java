package es.uca.iw.model;

import java.util.UUID;

public class CustomerLine {
    UUID Id;
    public UUID getId() { return Id; }
    public void setId(UUID Id) { this.Id = Id; }

    private String name;
    public String getName() { return name; }
    public void setName(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        this.name = name;
    }

    private String surname;
    public String getSurname() { return surname; }
    public void setSurname(String surname) {
        if (surname == null || surname.isEmpty())
            throw new IllegalArgumentException("Los apellidos no pueden ser nulos o vacíos");
        this.surname = surname;
    }

    private String carrier = "PhoneNet";
    public String getCarrier() { return carrier; }

    private String phoneNumber;
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty() || phoneNumber.length() != 9)
            throw new IllegalArgumentException("Formato del número de teléfono incorrecto");
        this.phoneNumber = phoneNumber;
    }
}
