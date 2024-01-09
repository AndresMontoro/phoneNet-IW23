package es.uca.iw.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;

@Entity
public class Product {

    public enum ProductType {
        FIBRA, MOVIL, FIJO
    }

    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    public Long getId() { return id; }

    @Column(unique = true, nullable = false)
    @NotEmpty(message = "Incluya el nombre, por favor")
    private String name;
    public String getName() { return name; }
    public void setName(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        this.name = name;
    }

    @Column(unique = true, nullable = false)
    @NotEmpty(message = "Incluya la descripción, por favor")
    private String description;
    public String getDescription() { return description; }
    public void setDescription(String description) {
        if (description == null || description.isEmpty())
            throw new IllegalArgumentException("La descripción no puede ser nula o vacía");
        this.description = description;
    }

    @NotEmpty(message = "Incluya la imagen, por favor")
    @Column(unique = true, nullable = false)
    private String image;
    public String getImage() { return image; }
    public void setImage(String image) {
        if (image == null || image.isEmpty())
            throw new IllegalArgumentException("La imagen no puede ser nula o vacía");
        this.image = image;
    }

    @NotNull(message = "Incluya el precio, por favor")
    private BigDecimal price;
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("El precio no puede ser negativo");
        this.price = price;
    }

    private BigDecimal dataPenaltyPrice;
    public BigDecimal getDataPenaltyPrice() { return dataPenaltyPrice; }
    public void setDataPenaltyPrice(BigDecimal dataPenaltyPrice) {
        if (dataPenaltyPrice == null || dataPenaltyPrice.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("El precio no puede ser negativo");
        this.dataPenaltyPrice = dataPenaltyPrice;
    }

    @NotNull(message = "Incluya el limite de datos")
    private int dataUsageLimit;
    public int getDataUsageLimit() { return dataUsageLimit; }
    public void setDataUsageLimit(int dataUsageLimit) {
        if (dataUsageLimit < 0)
            throw new IllegalArgumentException("El límite no puede ser negativo");
        this.dataUsageLimit = dataUsageLimit;
    }

    private BigDecimal callPenaltyPrice;
    public BigDecimal getCallPenaltyPrice() { return callPenaltyPrice; }
    public void setCallPenaltyPrice(BigDecimal callPenaltyPrice) {
        if (callPenaltyPrice == null || callPenaltyPrice.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("El precio no puede ser negativo");
        this.callPenaltyPrice = callPenaltyPrice;
    }

    @NotNull(message = "Incluya el limite de llamadas")
    private int callLimit;
    public int getCallLimit() { return callLimit; }
    public void setCallLimit(int callLimit) {
        if (callLimit < 0)
            throw new IllegalArgumentException("El límite no puede ser negativo");
        this.callLimit = callLimit;
    }

    private int routerSpeed = 0;
    public int getRouterSpeed() { return routerSpeed; }
    public void setRouterSpeed(int routerSpeed) {
        if (routerSpeed < 0)
            throw new IllegalArgumentException("La velocidad no puede ser negativa");
        this.routerSpeed = routerSpeed;
    }

    @Column(nullable = false)
    private boolean available;
    public boolean getAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @ElementCollection(targetClass = ProductType.class)
    @CollectionTable(name = "product_type", joinColumns = @JoinColumn(name = "product_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    @NotEmpty(message = "Incluya el tipo, por favor")
    @Fetch(FetchMode.SUBSELECT)
    private Set<ProductType> productType = new HashSet<>();
    public Set<ProductType> getProductType() { return productType; }
    public void setProductType(Set<ProductType> productType) { 
        this.productType = productType; 
    }

    // @ManyToMany(fetch = FetchType.EAGER)
    // private Set<ProductType> productType = new HashSet<>();
    // public Set<ProductType> getProductType() { return productType; }
    // public void setProductType(Set<ProductType> productType) { 
    //     this.productType = productType; 
    // }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) { 
        if (this == obj) return true;
        if (obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        Product other = (Product) obj;
        return Objects.equals(id, other.id);
    }
}
