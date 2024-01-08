package es.uca.iw.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void saveProduct(Product producto) {
        productRepository.save(producto);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<Product> findByIdAndProductType(Long id, Product.ProductType productType) {
        return productRepository.findByIdAndProductType(id, productType);
    }
 
    public void saveProductWithDetails(String name, String description, String imageUrl, BigDecimal price,
           Integer CallLimit , Integer DataUsageLimit, BigDecimal callPenaltyPrice, BigDecimal dataPenaltyPrice, boolean available, Set<Product.ProductType> productTypes) {
        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setDescription(description);
        newProduct.setImage(imageUrl);
        newProduct.setPrice(price);
        newProduct.setCallLimit(CallLimit);
        newProduct.setDataUsageLimit(DataUsageLimit);
        newProduct.setCallPenaltyPrice(callPenaltyPrice);
        newProduct.setDataPenaltyPrice(dataPenaltyPrice);
        newProduct.setAvailable(available);
        newProduct.setProductType(productTypes);

        saveProduct(newProduct);
    }

    public void editProductWithDetails(Long id, String newName, String newDescription, String newImageUrl, BigDecimal newPrice,
                                       int newCallLimit, int newDataUsageLimit,
                                       BigDecimal newCallPenaltyPrice, BigDecimal newDataPenaltyPrice, boolean newAvailable) {
        Optional<Product> optionalProduct = findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(newName);
            product.setDescription(newDescription);
            product.setImage(newImageUrl);
            product.setPrice(newPrice);
            product.setCallLimit(newCallLimit);
            product.setDataUsageLimit(newDataUsageLimit);
            product.setCallPenaltyPrice(newCallPenaltyPrice);
            product.setDataPenaltyPrice(newDataPenaltyPrice);
            product.setAvailable(newAvailable);

            saveProduct(product);
        }
    }
}

