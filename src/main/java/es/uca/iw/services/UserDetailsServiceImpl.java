package es.uca.iw.services;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;

import es.uca.iw.data.UserRepository;
import es.uca.iw.data.UserRoleRepository;
import es.uca.iw.model.User;
import es.uca.iw.model.UserRole;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public UserDetailsServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository,  PasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByusername(username);
    }

    public boolean existsByUsername(String username) {
        return userRepository.findByusername(username).isPresent();
    }

    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean existsByDni(String dni) {
        return userRepository.findByDni(dni).isPresent();
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(userRepository.findByusername(username).get().getAuthorities());
        return userRepository.findByusername(username).orElseThrow(() ->
            new UsernameNotFoundException("No user present with username: " + username));
    }

    // private static List<GrantedAuthority> grantedAuthorities(User user) {
    //     return user.getRoles().stream()
    //         .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
    //         .collect(Collectors.toList());
    // } 

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        if (userRepository.findByusername(user.getUsername()).isPresent())
            return false;
        userRepository.save(user);
        return true;
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

        if (actualUser == null) 
            throw new IOException("El usuario no existe");           
        
        if (!bCryptPasswordEncoder.matches(oldPassword, actualUser.getPassword())) {
            System.err.println("La contraseña antigua no es correcta" + oldPassword);
            throw new IllegalArgumentException("La contraseña antigua no es correcta");
        }
           
        actualUser.setPassword(newPassword);
        userRepository.save(actualUser);
    }

    public void saveUserWithDetails(String name, String surname, String username, String password, String dni, 
        String email, String phoneNumber, Set<UserRole> roles) {
        if (existsByUsername(username))
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        if (existsByDni(dni))
            throw new IllegalArgumentException("El DNI ya está en uso");
        if (existsByEmail(email))
            throw new IllegalArgumentException("El email ya está en uso");

        Set<UserRole> persistedRoles = new HashSet<>();
        for (UserRole role : roles) {
            UserRole persistedRole = userRoleRepository.findByRole(role.getRole()).orElse(null);

            if (persistedRole == null) {
                userRoleRepository.save(role);
                persistedRoles.add(role);
            } else {
                persistedRoles.add(persistedRole);
            }
        } 
        
        User newUser = new User();
        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setUsername(username);
        password = bCryptPasswordEncoder.encode(password);
        newUser.setPassword(password);
        newUser.setDni(dni);
        newUser.setEmail(email);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setRoles(persistedRoles);
        userRepository.save(newUser);
        // Set<UserRole> userRoles = roles.stream().map(role -> new UserRole(role, newUser)).collect(Collectors.toSet());
        // userRoleRepository.saveAll(userRoles);
        // newUser.setRoles(userRoles);
        // userRepository.save(newUser);
    }

    public void editUserWithDetails(Long id, String newName, String newSurname, String newUsername, String newPassword, String newDni, String newEmail, String phoneNumber, Set<UserRole.Role> roles) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (existsByUsername(newUsername) && !optionalUser.get().getUsername().equals(newUsername))
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        if (existsByDni(newDni) && !optionalUser.get().getDni().equals(newDni))
            throw new IllegalArgumentException("El DNI ya está en uso");
        if (existsByEmail(newEmail) && !optionalUser.get().getEmail().equals(newEmail))
            throw new IllegalArgumentException("El email ya está en uso");

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(newName);
            user.setSurname(newSurname);
            user.setUsername(newUsername);
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            user.setDni(newDni);
            user.setEmail(newEmail);
            user.setPhoneNumber(phoneNumber);
            //userRoleRepository.deleteAll(user.getRoles());
            // userRepository.save(user);

            // Set<UserRole> userRoles = roles.stream().map(role -> new UserRole(role)).collect(Collectors.toSet());
            // userRoleRepository.saveAll(userRoles);
            // user.setRoles(userRoles);

            user.getRoles().clear();
            for (UserRole.Role role : roles) {
                UserRole userRole = userRoleRepository.findByRole(role).orElse(null);
                if (userRole != null) {
                    user.getRoles().add(userRole);
                }
            }
            userRepository.save(user);
        }
    }

    //borrar usuario con el id
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userRepository.delete(user);
        }
    }




    public void updateUser(User user) {
        userRepository.save(user);
    }

}
