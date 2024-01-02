package es.uca.iw.services;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.uca.iw.model.Product;
import es.uca.iw.model.User;
import jakarta.transaction.Transactional;
import es.uca.iw.model.CallRecord;
import es.uca.iw.model.Contract;
import es.uca.iw.model.CustomerLine;
import es.uca.iw.model.DataUsageRecord;

import org.springframework.scheduling.annotation.Scheduled;
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

    public void save(Contract contract) {
        contractRepository.save(contract);
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

    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    private String createRandomPhoneNumber() {
        String phoneNumber = "";
        for (int i = 0; i < 9; i++) {
            phoneNumber += (int) (Math.random() * 10);
        }
        return phoneNumber;
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
        newContract.setLastBillUpdate(getFirstDayOfMonth());
        contractRepository.save(newContract);
    }

    void deleteContractFromApi(Contract contract) throws Exception {
        try {
            restTemplate.delete("http://omr-simulator.us-east-1.elasticbeanstalk.com/" + contract.getApiId().toString() + "?carrier=PhoneNet");
        } catch (HttpServerErrorException hsee) {
            throw new Exception("No se ha podido realizar el registro (API caida)");
        } catch (HttpClientErrorException hcee) {
            throw new Exception("Error al conectar con el sistema");
        } catch(Exception e) {
            e.printStackTrace();
        }
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
        
        deleteContractFromApi(contract);
        callRecordRepository.deleteByContractId(contract);
        dataUsageRecordRepository.deleteByContractId(contract);
        contractRepository.delete(contract);
    }

    public Date getFirstDayOfMonth() {
        Date actualDate = new Date();

        // Convertir Date a Calendar para manipularlo más fácilmente
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(actualDate);

        // Establecer el día del mes en 1 para obtener el primer día del mes
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);

        // Obtener la fecha resultante
        actualDate = calendar.getTime();
        return actualDate;
    }

    private Date addMonthToDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    // No vamos a actualizar nada cada vez que queramos obtener los datos de consumo
    public Integer getDataConsumption(Contract contract, Date startDate) {
        Date searchingEndDate = addMonthToDate(startDate);
        
        Integer megaBytes = 0;      
        List<DataUsageRecord> dataUsageRecords = dataUsageRecordRepository.findByContractAndDate(contract, startDate, searchingEndDate);
        for(DataUsageRecord dataUsageRecord : dataUsageRecords) {
            megaBytes = megaBytes + dataUsageRecord.getMegaBytes();
        }
        return megaBytes;
    }

    public Integer getContractCallConsumption(Contract contract, Date startDate) {
        Date searchingEndDate = addMonthToDate(startDate);

        Integer seconds = 0;
        List<CallRecord> callRecords = callRecordRepository.findByContractAndDate(contract, startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), 
            searchingEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        for (CallRecord callRecord : callRecords) {
            seconds += callRecord.getSeconds();
        }

        return seconds / 60;
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void updateAllContractsDataAndCallsUsage() throws Exception {
        List<Contract> contracts = contractRepository.findAll();
        for (Contract contract : contracts) {
            if (contract.getEndDate() != null && contract.getEndDate().before(new Date())) {
                contractRepository.delete(contract);
                continue;
            }   
            
            String dataRequestUrl = "http://omr-simulator.us-east-1.elasticbeanstalk.com/" + contract.getApiId().toString() 
                + "/datausagerecords?carrier=PhoneNet" 
                + (contract.getLastCallDataUpdate() != null ? "&startDate=" + contract.getLastCallDataUpdate().toString() : "");

            String callRecordRequest = "http://omr-simulator.us-east-1.elasticbeanstalk.com/" + contract.getApiId().toString() 
                + "/callrecords?carrier=PhoneNet" 
                + (contract.getLastCallDataUpdate() != null ? "&startDate=" + contract.getLastCallDataUpdate().toString() : "");
            
            try {
                DataUsageRecord[] dataUsageRecords = restTemplate.getForObject(dataRequestUrl, DataUsageRecord[].class);
                CallRecord[] callRecords = restTemplate.getForObject(callRecordRequest, CallRecord[].class);

                System.out.println("----------DATOS DE CALL RECORDS----------\n" );
                for (CallRecord callRecord : callRecords) {
                    System.out.println(callRecord.getDestinationPhoneNumber() + " " + callRecord.getSeconds() + " " + callRecord.getDateTime());
                }
                System.out.println("----------FIN DE CALL RECORDS---------");

                for (DataUsageRecord dataUsageRecord : dataUsageRecords) {
                    contract.getDataUsageRecords().add(dataUsageRecord);
                    dataUsageRecord.setContract(contract);
                    dataUsageRecordRepository.save(dataUsageRecord);
                }

                for (CallRecord callRecord : callRecords) {
                    contract.getCallRecord().add(callRecord);
                    callRecord.setContract(contract);
                    callRecordRepository.save(callRecord);
                }

                contract.setLastCallDataUpdate(new Date());
                contractRepository.save(contract);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}