package es.uca.iw.model;

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


    // @ManyToOne
    // private User user;

    public UserRole() {
        // Constructor
    }

    public UserRole(Role role) {
        this.role = role;
    }

}
