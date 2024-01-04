package es.uca.iw.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserRole {
    public enum Role { ADMIN, USER } 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Long getId() { return id; }

    @Enumerated(EnumType.STRING)
    private Role role;
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) { 
        if (this == obj) return true;
        if (obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        UserRole other = (UserRole) obj;
        return Objects.equals(id, other.id);
    }

    public UserRole() {}

    public UserRole(Role role) {
        this.role = role;
    }

}
