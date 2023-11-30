package es.uca.iw.services;

import java.util.List;
import org.springframework.stereotype.Service;
import es.uca.iw.model.Product;
import es.uca.iw.model.User;
import es.uca.iw.data.ProductRepository;
import es.uca.iw.data.UserRepository;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public boolean hireProduct(String productName) {
        Product product = productRepository.findByname(productName).orElse(null);
        if (product == null)
            return false;
        
        // En un futuro se obtendra el usuario que ha iniciado la sesion
        User actualUser = userRepository.findByusername("andresmontoro").orElse(null);
        if (actualUser == null)
            return false;

        actualUser.getProducts().add(product);
        userRepository.save(actualUser);
        return true;
    }
}
