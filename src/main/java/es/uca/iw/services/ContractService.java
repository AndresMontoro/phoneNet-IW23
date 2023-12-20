package es.uca.iw.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.uca.iw.model.Product;
import es.uca.iw.model.User;
import es.uca.iw.model.Contract;
import es.uca.iw.model.PhoneNumber;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.uca.iw.data.ContractRepository;
import es.uca.iw.data.ProductRepository;
import es.uca.iw.data.PhoneNumberRepository;

@Service
public class ContractService {
    private ProductRepository productRepository;
    private UserDetailsServiceImpl userDetailsServiceImpl;
    private ContractRepository contractRepository;
    private PhoneNumberRepository phoneNumberRepository;

    // private final String API_URL = "http://omr-simulator.us-east-1.elasticbeanstalk.com/";
    private RestTemplate restTemplate;

    public ContractService(ProductRepository productRepository, UserDetailsServiceImpl userDetailsServiceImpl, 
        ContractRepository contractRepository, PhoneNumberRepository phoneNumberRepository, RestTemplate restTemplate) {
        this.productRepository = productRepository;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.contractRepository = contractRepository;
        this.phoneNumberRepository = phoneNumberRepository;
        this.restTemplate = restTemplate;
    }

    public List<Product> getContractProducts() {
        User actualUser = userDetailsServiceImpl.getAuthenticatedUser().orElse(null);
        List<Contract> contracts = contractRepository.findByUser(actualUser);
        List<Product> contractProducts = new ArrayList<Product>();

        for (Contract contract : contracts) {
            contractProducts.add(contract.getProduct());
        }

        return contractProducts;
    }

    public List<Contract> getContracts() {
        User actualUser = userDetailsServiceImpl.getAuthenticatedUser().orElse(null);
        List<Contract> contracts = contractRepository.findByUser(actualUser);
        return contracts;
    }

    public boolean hireProduct(String productName) {
        Product product = productRepository.findByname(productName).orElse(null);
        if (product == null)
            return false;
        
        User actualUser = userDetailsServiceImpl.getAuthenticatedUser().orElse(null);
        if (actualUser == null)
            return false;

        Contract contract = contractRepository.findByUserAndProduct(actualUser, product).orElse(null);
        if (contract != null)
            return false;

        PhoneNumber phoneNumber = phoneNumberRepository.findFirstAvailableNumber().orElse(null);
        if(phoneNumber == null) return false;

        Contract newContract = new Contract();
        newContract.setUser(actualUser);
        newContract.setProduct(product);
        newContract.setPhoneNumber(phoneNumber);
        
        // Anadir linea a la API


        // Anadir apiUrl al contrato

        contractRepository.save(newContract);
        return true;
    }

    public boolean unhireProduct(String productName) {
        Product product = productRepository.findByname(productName).orElse(null);
        if (product == null)
            return false;
        
        User actualUser = userDetailsServiceImpl.getAuthenticatedUser().orElse(null);
        if (actualUser == null)
            return false;

        Contract contract = contractRepository.findByUserAndProduct(actualUser, product).orElse(null);
        if (contract == null)
            return false;

        contractRepository.delete(contract);
        return true;
    }

    // No vamos a actualizar nada cada vez que queramos obtener los datos de consumo
    public void getContractDataConsumption(Contract contract) throws IOException {       
        // Obtenemos el consumo de la linea (consulta a la API)


        // Devolvemos el consumo
    }
}