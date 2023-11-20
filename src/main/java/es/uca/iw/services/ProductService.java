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
        p1.setName("Tarifa de vuelta al cole");
        p1.setAvailable(true);
        p1.setDescription("Tarifa todo en uno para estudiantes. Garantiza una experiencia de navegación de 600Mb simétricos, llamadas ilimitadas en tu fijo y móvil y 20GB de datos móviles.");
        p1.setImage("https://www.elmira.es/asset/thumbnail,992,558,center,center/media/elmira/images/2023/04/28/2023042809092957303.jpg");
        p1.setPrice(new BigDecimal(30.99));

        // Crea un set de tipos de productos
        Set<Product.ProductType> productTypes = new HashSet<Product.ProductType>();
        productTypes.add(Product.ProductType.FIBRA);
        p1.setProductType(productTypes);

        productRepository.save(p1);
    }
}
