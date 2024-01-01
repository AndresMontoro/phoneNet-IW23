package es.uca.iw.services;
import es.uca.iw.data.ProductRepository;
import es.uca.iw.model.Product;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EditarProductosService {
    private final ProductRepository productRepository;

    public EditarProductosService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void saveProduct(Product producto) {
        productRepository.save(producto);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
}
