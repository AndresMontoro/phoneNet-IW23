package es.uca.iw.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import es.uca.iw.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByid(Long id);
    Optional<Product> findByname(String name);
    Optional<Product> findByIdAndProductType(Long id, Product.ProductType productType);
}
