package es.uca.iw.data;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import es.uca.iw.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByAvailable(boolean available);
}
