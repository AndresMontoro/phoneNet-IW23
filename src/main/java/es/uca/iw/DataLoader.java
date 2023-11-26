/* Comentado para que no inserte datos cada vez que arranca el server*/

// package es.uca.iw;

// import java.math.BigDecimal;
// import java.util.HashSet;
// import java.util.Set;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;

// import es.uca.iw.data.ProductRepository;
// import es.uca.iw.data.UserRepository;
// import es.uca.iw.model.Product;

// @Component
// public class DataLoader implements CommandLineRunner{
//     @Autowired
//     public ProductRepository productRepository;

//     @Autowired
//     public UserRepository userRepository;



//     @Override
//     public void run(String... args) throws Exception {
//         // User user = new User();
//         // user.setName("Andrés");
//         // user.setSurname("Montoro Venegas");
//         // user.setUsername("andresmontoro");
//         // user.setPassword(passwordEncoder.encode("andresmontoro"));
//         // user.setRole(User.Role.USER);
//         // user.setEmail("andresmontoro@gmail.com");
//         // user.setDni("32094459Y");

//         // Product product = productRepository.findByname("Tarifa de verano").orElse(null);
//         // if (product != null)
//         //     user.getProducts().add(product);
        
//         // userRepository.save(user);

//         Product product = new Product();
//         product.setAvailable(true);
//         product.setDescription("Tarifa especial para programadores");
//         product.setName("Hola mundo");
//         product.setImage("https://images.unsplash.com/photo-1519681393784-d120267933ba?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw");
//         product.setPrice(new BigDecimal(30.45));
//         product.getProductType().add(Product.ProductType.FIBRA);
//         product.getProductType().add(Product.ProductType.MOVIL);
//         productRepository.save(product);
//     }
// }