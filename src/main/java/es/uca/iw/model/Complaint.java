package es.uca.iw.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
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
    @JoinColumn(nullable = false)
    private User user;
    public void setUser(User user) {
        this.user = user;
    }


    @Column(nullable = false)
    private String description;
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }



    @Column(nullable = false)
    private LocalDate creationDate;
    public LocalDate getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintStatus status;
    public ComplaintStatus getStatus() { return status; }
    public void setStatus(ComplaintStatus status) {
        this.status = status;
    }
    

    
    @Column(nullable = true)
    private List<String> comments;
    public List<String> getComments() { return comments; }
    public void setComments(List<String> comments) { 
        this.comments = comments; 
    }


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