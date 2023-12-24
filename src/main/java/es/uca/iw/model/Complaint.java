package es.uca.iw.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;



@Entity
public class Complaint {
    public enum ComplaintStatus {
        EN_ESPERA, EN_PROCESO, RESUELTA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Long getId() { return id; }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    //@Column(name = "description", nullable = false)
    private String description;
    public String getDescription() { return description; }


    //@Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;
    public LocalDate getCreationDate() { return creationDate; }


    @Enumerated(EnumType.STRING)
    //@Column(name = "status", nullable = false)
    private ComplaintStatus status;
    public ComplaintStatus getStatus() { return status; }


    //@Column(name = "comments", nullable = true)
    private String comments;
    public String getComments() { return comments; }

    // Otros métodos y getters/setters

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Complaint complaint = (Complaint) obj;
        return Objects.equals(id, complaint.id);
    }
}