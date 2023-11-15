package es.uca.iw.model;

import javax.persistence.*;

import jakarta.validation.constraints.NotEmpty;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long _id;
    public Long getId() { return _id; }

    @NotEmpty(message = "Incluya el nombre, por favor")
    private String _name;
    public String getName() { return _name; }
    public void setName(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        _name = name;
    }

    @NotEmpty(message = "Incluya los apellidos, por favor")
    private String _surname;
    public String getSurname() { return _surname; }
    public void setSurname(String surname) {
        if (surname == null || surname.isEmpty())
            throw new IllegalArgumentException("Los apellidos no pueden ser nulos o vacíos");
        _surname = surname;
    }

    @NotEmpty(message = "Incluya el nombre de usuario, por favor")
    private String _username;
    public String getUsername() { return _username; }
    public void setUsername(String username) {
        if (username == null || username.isEmpty() || username.length() < 8)
            throw new IllegalArgumentException("El nombre de usuario no válido");
        _username = username;
    }
   
    @NotEmpty(message = "Incluya la contraseña, por favor")
    private String _password;
    public String getPassword() { return _password; }


    @NotEmpty
    private String _role;   // EMPLEADO | USUARIO
    public String getRole() { return _role; }
    public void setRole(String role) {
        if (role == null || role.isEmpty())
            throw new IllegalArgumentException("El rol no puede ser nulo o vacío");
        _role = role;
    }

    private String _dni;
    public String getDni() { return _dni; }
    public void setString(String dni) {
        if (dni == null || dni.isEmpty() || dni.length() != 9)
            throw new IllegalArgumentException("El dni no puede ser nulo o vacío");
        _dni = dni;
    }

    private String _email;
    public String getEmail() { return _email; }
    public void setEmail(String email) {
        if (email == null || email.isEmpty())
            throw new IllegalArgumentException("El email no puede ser nulo o vacío");
        _email = email;
    }

    // Falta poner tamannio maximo a los datos
}