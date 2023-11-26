package es.uca.iw.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


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
    private Long _id;

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
    @Column(nullable = false)
    private BigDecimal price;
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("El precio no puede ser negativo");
        this.price = price;
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
    private Set<ProductType> productType = new HashSet<>();
    public Set<ProductType> getProductType() { return productType; }
    public void setProductType(Set<ProductType> productType) { 
        this.productType = productType; 
    }
}
