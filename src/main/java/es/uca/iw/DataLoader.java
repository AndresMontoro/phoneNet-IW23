// /* Comentado para que no inserte datos cada vez que arranca el server*/

// package es.uca.iw;

// import java.math.BigDecimal;
// import java.util.Calendar;
// import java.util.Date;
// import java.util.HashSet;
// import java.util.List;
// import java.util.Set;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Component;
// import org.springframework.web.client.RestTemplate;

// import es.uca.iw.data.UserRepository;
// import es.uca.iw.data.UserRoleRepository;
// import es.uca.iw.model.User;
// import es.uca.iw.model.UserRole;
// import es.uca.iw.services.BillService;
// import es.uca.iw.services.ContractService;
// import es.uca.iw.model.CallRecord;

// import es.uca.iw.data.BillRepository;
// import es.uca.iw.data.ContractRepository;
// import es.uca.iw.model.Bill;

// @Component
// public class DataLoader implements CommandLineRunner {

//     // private final UserRepository userRepository;
//     // private final PasswordEncoder bCryptPasswordEncoder;
//     // private final UserRoleRepository userRoleRepository;
//     // private final PhoneNumberRepository phoneNumberRepository;
//     private ContractRepository contractRepository;
//     private BillRepository billRepository;
//     private BillService billService;
//     private ContractService contractService;

//     @Autowired
//     private RestTemplate restTemplate;

//     DataLoader(ContractRepository contractRepository, BillRepository billRepository, BillService billService, ContractService contractService) {
//         // this.userRepository = userRepository;
//         // this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//         // this.userRoleRepository = userRoleRepository;
//         // this.phoneNumberRepository = phoneNumberRepository;
//         this.contractRepository = contractRepository;
//         this.billRepository = billRepository;
//         this.billService = billService;
//         this.contractService = contractService;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         // User user = new User();
//         // user.setUsername("admin");
//         // user.setPassword(bCryptPasswordEncoder.encode("admin"));
//         // user.setName("Admin");
//         // user.setSurname("Admin");
//         // user.setDni("32094459Y");
//         // user.setEmail("AdminPhoneNet@gmail.com");
//         // Set<UserRole> roles = new HashSet<>();
//         // UserRole userRole = new UserRole();
//         // userRole.setRole(UserRole.Role.ADMIN);
//         // userRoleRepository.save(userRole);
        
//         // roles.add(userRole);
//         // user.setRoles(roles);
//         // userRepository.save(user);

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

//         // PhoneNumber phoneNumber = new PhoneNumber();
//         // phoneNumber.setNumber("679109619");
//         // phoneNumber.setAvailable(true);
//         // phoneNumberRepository.save(phoneNumber);

//         // phoneNumber = new PhoneNumber();
//         // phoneNumber.setNumber("601488514");
//         // phoneNumber.setAvailable(true);
//         // phoneNumberRepository.save(phoneNumber);

//         // List<CallRecord> respuesta = restTemplate.getForObject("http://omr-simulator.us-east-1.elasticbeanstalk.com/10e68f12-b113-42ba-bf9b-f4bee0cb4f74/callrecords?carrier=UCA", 
//         //     List.class);
        
//         // System.out.println(respuesta);

//         // contractService.updateAllContractsDataAndCallsUsage();

//         // Bill bill = new Bill();
//         // Calendar calendar = Calendar.getInstance();
//         // calendar.setTime(new Date());
//         // calendar.set(Calendar.DAY_OF_MONTH, 1);
//         // calendar.set(Calendar.HOUR_OF_DAY, 0);
//         // calendar.set(Calendar.MINUTE, 0);

//         // bill.setDate(calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//         // bill.setdataConsumed(200);
//         // bill.setminutesConsumed(100);
//         // bill.setContract(contractRepository.findById(9L).orElse(null));

//         // billRepository.save(bill);
//         // billService.generateBills();
//         // contractService.updateAllContractsDataAndCallsUsage();
//         // billService.generateBills();
//         contractService.updateAllContractsDataAndCallsUsage();
//         // billService.generateBills();
//     }
// }