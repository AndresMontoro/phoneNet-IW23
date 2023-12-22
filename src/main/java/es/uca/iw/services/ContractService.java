package es.uca.iw.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import es.uca.iw.model.Product;
import es.uca.iw.model.User;
import es.uca.iw.model.Contract;
import es.uca.iw.model.CustomerLine;
import es.uca.iw.model.PhoneNumber;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
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

    public void hireProduct(String productName) throws Exception {
        Product product = productRepository.findByname(productName).orElse(null);
        if (product == null)
            throw new IllegalArgumentException("El producto no existe");
        
        User actualUser = userDetailsServiceImpl.getAuthenticatedUser().orElse(null);
        if (actualUser == null)
            throw new IllegalArgumentException("No ha iniciado sesión");

        Contract contract = contractRepository.findByUserAndProduct(actualUser, product).orElse(null);
        if (contract != null)
            throw new IllegalArgumentException("Ya tiene contratado este producto");

        Contract newContract = new Contract();
        newContract.setUser(actualUser);
        newContract.setProduct(product);

        // Creamos Customer Line
        CustomerLine customerLine = new CustomerLine();
        customerLine.setName(actualUser.getName());
        customerLine.setSurname(actualUser.getSurname());
        customerLine.setPhoneNumber(createRandomPhoneNumber());
        
        // Anadir linea a la API
        try {
            customerLine = restTemplate.postForObject("http://omr-simulator.us-east-1.elasticbeanstalk.com/", 
                customerLine, CustomerLine.class);
        } catch (HttpServerErrorException hsee) {
            throw new Exception("No se ha podido realizar el registro (API caida)");
        } catch (HttpClientErrorException hcee) {
            throw new Exception("No se ha podido registrar el número de teléfono");
        } catch(Exception e) {
            e.printStackTrace();
        }

        // Anadir apiId a la linea
        newContract.setPhoneNumber(customerLine.getPhoneNumber());
        newContract.setApiId(customerLine.getId());
        contractRepository.save(newContract);
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

    public String createRandomPhoneNumber() {
        String phoneNumber = "";
        for (int i = 0; i < 9; i++) {
            phoneNumber += (int) (Math.random() * 10);
        }
        return phoneNumber;
    }
}