// package es.uca.iw.services;
// import es.uca.iw.data.ProductRepository;
// import es.uca.iw.model.Product;

// import java.math.BigDecimal;
// import java.util.Optional;
// import org.springframework.stereotype.Service;
// import java.util.Set;



// @Service
// public class EditarProductosService {
//     private final ProductRepository productRepository;

//     public EditarProductosService(ProductRepository productRepository) {
//         this.productRepository = productRepository;
//     }

//     public void deleteProduct(Long id) {
//         productRepository.deleteById(id);
//     }

//     public void saveProduct(Product producto) {
//         productRepository.save(producto);
//     }



//     public Optional<Product> findById(Long id) {
//         return productRepository.findById(id);
//     }
 
//     public void saveProductWithDetails(String name, String description, String imageUrl, BigDecimal price, BigDecimal callPrice,
//            BigDecimal dataUsagePrice, Integer CallLimit , Integer DataUsageLimit, BigDecimal callPenaltyPrice, BigDecimal dataPenaltyPrice, boolean available, Set<Product.ProductType> productTypes) {
//         Product newProduct = new Product();
//         newProduct.setName(name);
//         newProduct.setDescription(description);
//         newProduct.setImage(imageUrl);
//         newProduct.setPrice(price);
//         newProduct.setCallPrice(callPrice);
//         newProduct.setDataUsagePrice(dataUsagePrice);
//         newProduct.setCallLimit(CallLimit);
//         newProduct.setDataUsageLimit(DataUsageLimit);
//         newProduct.setCallPenaltyPrice(callPenaltyPrice);
//         newProduct.setDataPenaltyPrice(dataPenaltyPrice);
//         newProduct.setAvailable(available);
//         newProduct.setProductType(productTypes);

//         saveProduct(newProduct);
//     }

    
//     public void editProductWithDetails(Long id, String newName, String newDescription, String newImageUrl, BigDecimal newPrice,
//                                        BigDecimal newCallPrice, BigDecimal newDataUsagePrice ,int newCallLimit, int newDataUsageLimit,
//                                        BigDecimal newCallPenaltyPrice, BigDecimal newDataPenaltyPrice, boolean newAvailable) {
//         Optional<Product> optionalProduct = findById(id);
//         if (optionalProduct.isPresent()) {
//             Product product = optionalProduct.get();
//             product.setName(newName);
//             product.setDescription(newDescription);
//             product.setImage(newImageUrl);
//             product.setPrice(newPrice);
//             product.setCallPrice(newCallPrice);
//             product.setDataUsagePrice(newDataUsagePrice);
//             product.setCallLimit(newCallLimit);
//             product.setDataUsageLimit(newDataUsageLimit);
//             product.setCallPenaltyPrice(newCallPenaltyPrice);
//             product.setDataPenaltyPrice(newDataPenaltyPrice);
//             product.setAvailable(newAvailable);

//             saveProduct(product);
//         }
//     }


// }
