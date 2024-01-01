package es.uca.iw.services;

import java.util.List;
import org.springframework.stereotype.Service;
import es.uca.iw.model.Product;
import es.uca.iw.data.ProductRepository;
import es.uca.iw.data.UserRepository;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}

