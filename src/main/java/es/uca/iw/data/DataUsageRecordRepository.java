package es.uca.iw.data;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.uca.iw.model.Contract;
import es.uca.iw.model.DataUsageRecord;

public interface DataUsageRecordRepository extends JpaRepository<DataUsageRecord, Long>{
    @Modifying
    @Query("DELETE FROM DataUsageRecord d WHERE d.contract = :contract")
    int deleteByContractId(@Param("contract") Contract contract);

    @Query("SELECT d FROM DataUsageRecord d WHERE d.contract = :contract AND d.date >= :startDate AND d.date < :endDate")
    List<DataUsageRecord> findByContractAndDate(@Param("contract") Contract contract, 
        @Param("startDate") Date searchingStartDate, @Param("endDate") Date searchingEndDate);
}
