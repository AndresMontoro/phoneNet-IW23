package es.uca.iw.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotEmpty;

@Entity
public class User { 
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    public Long getId() { return id; }

    @NotEmpty(message = "Incluya el nombre, por favor")
    private String name;
    public String getName() { return name; }
    public void setName(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        this.name = name;
    }

    @NotEmpty(message = "Incluya los apellidos, por favor")
    private String surname;
    public String getSurname() { return surname; }
    public void setSurname(String surname) {
        if (surname == null || surname.isEmpty())
            throw new IllegalArgumentException("Los apellidos no pueden ser nulos o vacíos");
        this.surname = surname;
    }

    @NotEmpty(message = "Incluya el nombre de usuario, por favor")
    @Column(unique = true)
    private String username;
    public String getUsername() { return username; }
    public void setUsername(String username) {
        if (username == null || username.isEmpty() || username.length() < 8)
            throw new IllegalArgumentException("El nombre de usuario no valido");
        this.username = username;
    }
   
    @NotEmpty(message = "Incluya la contrasenna, por favor")
    private String password;
    public String getPassword() { return password; }

    public enum Role { ADMIN, USER }  
    @NotEmpty
    @Enumerated(EnumType.STRING)
    private Role role;
    public Role getRole() { return role; }
    public void setRole(Role role) {
        if (role == null)
            throw new IllegalArgumentException("El rol no puede ser nulo o vacio");
        this.role = role;
    }

    private String dni;
    public String getDni() { return dni; }
    public void setString(String dni) {
        if (dni == null || dni.isEmpty() || dni.length() != 9)
            throw new IllegalArgumentException("DNI invalido");
        this.dni = dni;
    }

    @Column(unique = true)
    private String email;
    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (email == null || email.isEmpty())
            throw new IllegalArgumentException("El email no puede ser nulo o vacío");
        this.email = email;
    }

    // Falta poner tamannio maximo a los datos
}