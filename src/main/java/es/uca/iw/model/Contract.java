package es.uca.iw.model;

import java.util.Date;
import java.util.Calendar;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GenerationType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

@Entity
public class Contract {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    public Long getId() { return id; }

    @NotNull(message = "Incluya la fecha de inicio, por favor")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) {  // Deberiamos de hacer mas comprobaciones
        if (startDate == null)
            throw new IllegalArgumentException("La fecha de inicio no puede ser nula");
        this.startDate = startDate;
    }

    @NotNull(message = "Incluya la fecha de fin, por favor")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) {  // Deberiamos de hacer mas comprobaciones
        if (endDate == null)
            throw new IllegalArgumentException("La fecha de fin no puede ser nula");
        this.endDate = endDate;
    }

    @ManyToOne
    private User user;
    public User getUser() { return user; }
    public void setUser(User user) {
        if (user == null)
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        this.user = user;
    }

    @ManyToOne
    private Product product;
    public Product getProduct() { return product; }
    public void setProduct(Product product) {
        if (product == null)
            throw new IllegalArgumentException("El producto no puede ser nulo");
        this.product = product;
    }

    @PrePersist
    public void PrePersist() {
        // Actual date (milisenconds)
        startDate = new Date(Calendar.getInstance().getTime().getTime());

        // Add 1 year to the current date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.YEAR, 1);
        endDate = new Date(calendar.getTime().getTime());
    }
}

