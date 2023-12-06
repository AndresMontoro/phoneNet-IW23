package es.uca.iw.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import es.uca.iw.data.UserRepository;
import es.uca.iw.model.User;
import es.uca.iw.model.Product;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByusername(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Set<Product> getUserProducts(User user) {
        return user.getProducts();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
