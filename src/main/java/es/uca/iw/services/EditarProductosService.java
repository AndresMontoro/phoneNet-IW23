package es.uca.iw.services;
import es.uca.iw.data.ProductRepository;
// import java.util.List;
import es.uca.iw.model.Product;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class EditarProductosService {

    private final ProductRepository productRepository;

    public EditarProductosService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void editarNombre(Long id, String nuevoNombre) {
        Product producto = obtenerProductoPorId(id);
        if (producto != null) {
            producto.setName(nuevoNombre);
            guardarProducto(producto); /////
        }
    }

    public void editarDescripcion(Long id, String nuevaDescripcion) {
        Product producto = obtenerProductoPorId(id);
        if (producto != null) {
            producto.setDescription(nuevaDescripcion);
            guardarProducto(producto); /////
        }
    }

    // Agrega más métodos de edición según tus necesidades

    public void borrarProducto(Long id) {
        productRepository.deleteById(id);
    }

    private Product obtenerProductoPorId(Long id) {
        return productRepository.findById(id).orElse(null); //
    }

    @Transactional
    public void guardarProducto(Product producto) {
        productRepository.save(producto);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

}
