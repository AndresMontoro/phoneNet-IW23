package es.uca.iw.services;

import es.uca.iw.data.ProductRepository;
import java.util.List;
import es.uca.iw.model.Product;

public class ProductService {
    private ProductRepository _productRepository;

    public ProductService(ProductRepository productRepository) {
        _productRepository = productRepository;
    }

    // Metodo para consultar los productos que tiene un usuario

    // Metodo para consultar todos los productos disponibles en el sistema
    public List<Product> listAvailableProducts() {
        return _productRepository.findByAvailable(true);
    }
}
