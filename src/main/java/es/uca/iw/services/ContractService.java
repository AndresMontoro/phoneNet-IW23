package es.uca.iw.services;

import java.util.ArrayList;
import java.util.List;

import es.uca.iw.model.Product;
import es.uca.iw.model.User;
import es.uca.iw.model.Contract;

import org.springframework.stereotype.Service;

import es.uca.iw.data.ContractRepository;
import es.uca.iw.data.ProductRepository;

@Service
public class ContractService {
    private ProductRepository productRepository;
    private UserDetailsServiceImpl userDetailsServiceImpl;
    private ContractRepository contractRepository;

    public ContractService(ProductRepository productRepository, UserDetailsServiceImpl userDetailsServiceImpl, ContractRepository contractRepository) {
        this.productRepository = productRepository;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.contractRepository = contractRepository;
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

    public boolean hireProduct(String productName) {
        Product product = productRepository.findByname(productName).orElse(null);
        if (product == null)
            return false;
        
        // En un futuro se obtendra el usuario que ha iniciado la sesion
        User actualUser = userDetailsServiceImpl.getAuthenticatedUser().orElse(null);
        if (actualUser == null)
            return false;

        Contract contract = contractRepository.findByUserAndProduct(actualUser, product).orElse(null);
        if (contract != null)
            return false;

        Contract newContract = new Contract();
        newContract.setUser(actualUser);
        newContract.setProduct(product);
        contractRepository.save(newContract);
        return true;
    }

    public boolean unhireProduct(String productName) {
        Product product = productRepository.findByname(productName).orElse(null);
        if (product == null)
            return false;
        
        // En un futuro se obtendra el usuario que ha iniciado la sesion
        User actualUser = userDetailsServiceImpl.getAuthenticatedUser().orElse(null);
        if (actualUser == null)
            return false;

        Contract contract = contractRepository.findByUserAndProduct(actualUser, product).orElse(null);
        if (contract == null)
            return false;

        contractRepository.delete(contract);
        return true;
    }
}
