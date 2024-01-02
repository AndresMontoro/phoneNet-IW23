package es.uca.iw.data;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.uca.iw.model.CallRecord;
import es.uca.iw.model.Contract;

public interface CallRecordRepository extends JpaRepository<CallRecord, Long>{
    @Modifying
    @Query("DELETE FROM CallRecord c WHERE c.contract = :contract")
    int deleteByContractId(@Param("contract") Contract contract);

    @Query("SELECT c FROM CallRecord c WHERE c.contract = :contract AND c.dateTime >= :startDate AND c.dateTime < :endDate")
    List<CallRecord> findByContractAndDate(@Param("contract") Contract contract, 
        @Param("startDate") LocalDateTime date, @Param("endDate") LocalDateTime searchingEndDate);
} 

