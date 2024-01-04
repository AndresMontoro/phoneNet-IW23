package es.uca.iw.model;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class User implements UserDetails{ 
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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
        if (username == null || username.isEmpty() || username.length() < 5)
            throw new IllegalArgumentException("El nombre de usuario no valido");
        this.username = username;
    }
   
    @NotEmpty(message = "Incluya la contrasenna, por favor")
    private String password;
    public String getPassword() { return password; }
    public void setPassword(String password) {
        if (password == null || password.isEmpty() || password.length() < 5)
            throw new IllegalArgumentException("La contraseña no puede ser nula o vacía");
        this.password = password;
    }
 
    @NotNull(message = "Incluya el rol, por favor")
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserRole> roles = new HashSet<>();
    public Set<UserRole> getRoles() { return roles; }
    public void setRoles(Set<UserRole> roles) {
        if (roles == null)
            throw new IllegalArgumentException("El rol no puede ser nulo o vacio");
        this.roles = roles;
    }

    @Column(unique = true)
    private String dni;
    public String getDni() { return dni; }
    public void setDni(String dni) {
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

    private String phoneNumber;
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && phoneNumber != "" && phoneNumber.length() != 9)
            throw new IllegalArgumentException("Formato del número de teléfono incorrecto");
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) { 
        if (this == obj) return true;
        if (obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        User other = (User) obj;
        return Objects.equals(id, other.id);
    }
    
    @Override
    public List<GrantedAuthority> getAuthorities() {
        return this.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
            .collect(Collectors.toList());
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}