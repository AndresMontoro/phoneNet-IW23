package es.uca.iw.data;

import org.springframework.data.jpa.repository.JpaRepository;

import es.uca.iw.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {  
} 