package es.uca.iw.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.uca.iw.data.UserRepository;
import es.uca.iw.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public UserDetailsServiceImpl(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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

    public void changePassword(String oldPassword, String newPassword, String newPasswordConfirmation) throws IOException {
        if (!newPassword.equals(newPasswordConfirmation))
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        
        User actualUser = getAuthenticatedUser().orElse(null);
        newPassword = bCryptPasswordEncoder.encode(newPassword);
        // oldPassword = bCryptPasswordEncoder.encode(oldPassword);

        if (actualUser == null) 
            throw new IOException("El usuario no existe");           
        
        if (!bCryptPasswordEncoder.matches(oldPassword, actualUser.getPassword())) {
            System.err.println("La contraseña antigua no es correcta" + oldPassword);
            throw new IllegalArgumentException("La contraseña antigua no es correcta");
        }
           

        actualUser.setPassword(newPassword);
        userRepository.save(actualUser);
    }
}