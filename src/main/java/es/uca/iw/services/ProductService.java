package es.uca.iw.services;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import java.util.List;
import org.springframework.stereotype.Service;
import es.uca.iw.model.Product;
import es.uca.iw.data.ProductRepository;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void crearProductos() {
        Product p1 = new Product();
        p1.setName("Tarifa de veano");
        p1.setAvailable(true);
        p1.setDescription("Tarifa de verano para aprovechar el sol.");
        p1.setImage("https://images.unsplash.com/photo-1519681393784-d120267933ba?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw");
        p1.setPrice(new BigDecimal(21.50));

        // Crea un set de tipos de productos
        Set<Product.ProductType> productTypes = new HashSet<Product.ProductType>();
        productTypes.add(Product.ProductType.FIBRA);
        p1.setProductType(productTypes);

        productRepository.save(p1);
    }
}
