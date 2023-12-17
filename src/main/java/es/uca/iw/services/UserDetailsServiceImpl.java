package es.uca.iw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.uca.iw.data.UserRepository;
import es.uca.iw.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByusername(username);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByusername(username).orElseThrow(() ->
            new UsernameNotFoundException("No user present with username: " + username));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        if (userRepository.findByusername(user.getUsername()).isPresent())
            return false;
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    public Optional<User> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            return userRepository.findByusername(username);
        }

        else
            return Optional.empty();
    }

    public void updateActualUser(String username, String email, String phoneNumber) {
        User actualUser = getAuthenticatedUser().orElse(null);
        if (actualUser != null) {
            // Verificamos que el usuario
            if (userRepository.findByEmail(email).isPresent() && !actualUser.getEmail().equals(email) &&
                userRepository.findByusername(username).isPresent() && !actualUser.getUsername().equals(username))
                throw new IllegalArgumentException("El nombre de usuario o el email ya están en uso");
            else {
                actualUser.setUsername(username);
                actualUser.setEmail(email);
                actualUser.setPhoneNumber(phoneNumber);
                userRepository.save(actualUser);
            }
        }
    }
}