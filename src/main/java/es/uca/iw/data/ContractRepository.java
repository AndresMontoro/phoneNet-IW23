package es.uca.iw.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import es.uca.iw.model.Contract;
import es.uca.iw.model.User;
import es.uca.iw.model.Product;
import java.util.List;



public interface ContractRepository extends JpaRepository<Contract, Long>{
    Optional<Contract> findById(Long id);
    Optional<Contract> findByUserAndProduct(@Param("user") User user, @Param("product") Product product);
    List<Contract> findByUser(User user);
}
