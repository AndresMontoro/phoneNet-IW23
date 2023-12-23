package es.uca.iw.data;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.uca.iw.model.Contract;
import es.uca.iw.model.DataUsageRecord;

public interface DataUsageRecordRepository extends JpaRepository<DataUsageRecord, UUID>{
    @Modifying
    @Query("DELETE FROM DataUsageRecord d WHERE d.contract = :contract")
    int deleteByContractId(@Param("contract") Contract contract);
}
