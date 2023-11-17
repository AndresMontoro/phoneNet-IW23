package es.uca.iw.model;

import javax.persistence.*;

import jakarta.validation.constraints.NotEmpty;

@Entity
public class Product {
    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long _id;

    @Column(unique = true, nullable = false)
    @NotEmpty(message = "Incluya el nombre, por favor")
    private String _name;
    public String getName() { return _name; }
    public void setName(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        _name = name;
    }

    @Column(unique = true, nullable = false)
    @NotEmpty(message = "Incluya la descripción, por favor")
    private String _description;
    public String getDescription() { return _description; }
    public void setDescription(String description) {
        if (description == null || description.isEmpty())
            throw new IllegalArgumentException("La descripción no puede ser nula o vacía");
        _description = description;
    }

    @NotEmpty(message = "Incluya la imagen, por favor")
    @Column(unique = true, nullable = false)
    private String _image;
    public String getImage() { return _image; }
    public void setImage(String image) {
        if (image == null || image.isEmpty())
            throw new IllegalArgumentException("La imagen no puede ser nula o vacía");
        _image = image;
    }

    @NotEmpty(message = "Incluya el precio, por favor")
    @Column(nullable = false)
    private double _price;
    public double getPrice() { return _price; }
    public void setPrice(double price) {
        if (price < 0)
            throw new IllegalArgumentException("El precio no puede ser negativo");
        _price = price;
    }

    @Column(nullable = false)
    private boolean _available;
    public boolean getAvailable() { return _available; }
    public void setAvailable(boolean available) { _available = available; }
}
