package es.uca.iw.data;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.uca.iw.model.CallRecord;
import es.uca.iw.model.Contract;

public interface CallRecordRepository extends JpaRepository<CallRecord, UUID>{
    @Modifying
    @Query("DELETE FROM CallRecord c WHERE c.contract = :contract")
    int deleteByContractId(@Param("contract") Contract contract);
} 

