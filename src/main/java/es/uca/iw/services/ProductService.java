package es.uca.iw.services;

import java.math.BigDecimal;
import java.util.HashSet;
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

    public Set<Product.ProductType> getProductTypes(Long id) {
        Set<Product.ProductType> productTypes = new HashSet<>();
        if (findByIdAndProductType(id, Product.ProductType.FIBRA).isPresent())
            productTypes.add(Product.ProductType.FIBRA);
        if (findByIdAndProductType(id, Product.ProductType.MOVIL).isPresent())
            productTypes.add(Product.ProductType.MOVIL);
        if (findByIdAndProductType(id, Product.ProductType.FIJO).isPresent())
            productTypes.add(Product.ProductType.FIJO);
            
        return productTypes;
    }
 
    public void saveProductWithDetails(String name, String description, String imageUrl, BigDecimal price,
           Integer CallLimit , Integer DataUsageLimit, BigDecimal callPenaltyPrice, BigDecimal dataPenaltyPrice, Integer newProductRouterSpeed, boolean available, Set<Product.ProductType> productTypes) {
        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setDescription(description);
        newProduct.setImage(imageUrl);
        newProduct.setPrice(price);
        newProduct.setCallLimit(CallLimit);
        newProduct.setDataUsageLimit(DataUsageLimit);
        newProduct.setCallPenaltyPrice(callPenaltyPrice);
        newProduct.setDataPenaltyPrice(dataPenaltyPrice);
        newProduct.setRouterSpeed(newProductRouterSpeed);
        newProduct.setAvailable(available);
        newProduct.setProductType(productTypes);

        saveProduct(newProduct);
    }

    public void editProductWithDetails(Long id, String newName, String newDescription, String newImageUrl, BigDecimal newPrice,
                                       int newCallLimit, int newDataUsageLimit,
                                       BigDecimal newCallPenaltyPrice, BigDecimal newDataPenaltyPrice, int newRouterSpeed, boolean newAvailable) {
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
            product.setRouterSpeed(newRouterSpeed);
            product.setAvailable(newAvailable);

            saveProduct(product);
        }
    }
}
