package es.uca.iw.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.uca.iw.model.Product;
import es.uca.iw.model.User;
import jakarta.transaction.Transactional;
import es.uca.iw.model.Contract;
import es.uca.iw.model.CustomerLine;
import es.uca.iw.model.DataUsageRecord;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import es.uca.iw.data.CallRecordRepository;
import es.uca.iw.data.ContractRepository;
import es.uca.iw.data.DataUsageRecordRepository;
import es.uca.iw.data.ProductRepository;

@Service
public class ContractService {
    private ProductRepository productRepository;
    private UserDetailsServiceImpl userDetailsServiceImpl;
    private ContractRepository contractRepository;
    private RestTemplate restTemplate;
    private CallRecordRepository callRecordRepository;
    private DataUsageRecordRepository dataUsageRecordRepository;

    public ContractService(ProductRepository productRepository, UserDetailsServiceImpl userDetailsServiceImpl, 
        ContractRepository contractRepository, RestTemplate restTemplate, CallRecordRepository callRecordRepository,
        DataUsageRecordRepository dataUsageRepository) {
        this.productRepository = productRepository;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.contractRepository = contractRepository;
        this.restTemplate = restTemplate;
        this.callRecordRepository = callRecordRepository;
        this.dataUsageRecordRepository = dataUsageRepository;
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

    @Transactional
    public void unhireProduct(String productName) throws Exception {
        Product product = productRepository.findByname(productName).orElse(null);
        if (product == null)
            throw new IllegalArgumentException("El producto no existe");
        
        User actualUser = userDetailsServiceImpl.getAuthenticatedUser().orElse(null);
        if (actualUser == null)
            throw new IOException("No ha iniciado sesión");

        Contract contract = contractRepository.findByUserAndProduct(actualUser, product).orElse(null);
        if (contract == null)
            throw new IllegalArgumentException("No tiene contratado este producto");

        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("id", contract.getApiId().toString());
        requestParams.put("carrier", "PhoneNet");
        
        // Eliminamos la linea de la API
        try {
            restTemplate.delete("http://omr-simulator.us-east-1.elasticbeanstalk.com/" + contract.getApiId().toString() + "?carrier=PhoneNet");
        } catch (HttpServerErrorException hsee) {
            throw new Exception("No se ha podido realizar el registro (API caida)");
        } catch (HttpClientErrorException hcee) {
            throw new Exception("Error al conectar con el sistema");
        } catch(Exception e) {
            e.printStackTrace();
        }

        callRecordRepository.deleteByContractId(contract);
        dataUsageRecordRepository.deleteByContractId(contract);
        contractRepository.delete(contract);
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