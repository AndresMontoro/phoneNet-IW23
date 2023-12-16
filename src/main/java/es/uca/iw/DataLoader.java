/* Comentado para que no inserte datos cada vez que arranca el server*/

// package es.uca.iw;

// import java.util.HashSet;
// import java.util.Set;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Component;

// import es.uca.iw.data.UserRepository;
// import es.uca.iw.data.UserRoleRepository;
// import es.uca.iw.model.User;
// import es.uca.iw.model.UserRole;

// @Component
// public class DataLoader implements CommandLineRunner {

//     private final UserRepository userRepository;
//     private final PasswordEncoder bCryptPasswordEncoder;
//     private final UserRoleRepository userRoleRepository;

//     public DataLoader(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder, UserRoleRepository userRoleRepository) {
//         this.userRepository = userRepository;
//         this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//         this.userRoleRepository = userRoleRepository;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         User user = new User();
//         user.setUsername("admin");
//         user.setPassword(bCryptPasswordEncoder.encode("admin"));
//         user.setName("Admin");
//         user.setSurname("Admin");
//         user.setDni("32094459Y");
//         user.setEmail("AdminPhoneNet@gmail.com");
//         Set<UserRole> roles = new HashSet<>();
//         UserRole userRole = new UserRole();
//         userRole.setRole(UserRole.Role.ADMIN);
//         userRoleRepository.save(userRole);
        
//         roles.add(userRole);
//         user.setRoles(roles);
//         userRepository.save(user);

//         // user.setUsername("andres");
//         // user.setPassword(bCryptPasswordEncoder.encode("andres"));
//         // user.setName("Andrés");
//         // user.setSurname("Montoro Venegas");
//         // user.setDni("32094459Y");
//         // user.setEmail("andresmontoro@gmail.com");
//         // roles.remove(userRole);
//         // userRole.setRole(UserRole.Role.USER);
//         // userRoleRepository.save(userRole);
//         // roles.add(userRole);
//         // user.setRoles(roles);
//         // userRepository.save(user);
//     }
// }