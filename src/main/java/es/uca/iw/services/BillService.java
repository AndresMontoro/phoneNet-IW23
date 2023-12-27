package es.uca.iw.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import es.uca.iw.data.BillRepository;
import es.uca.iw.model.Bill;
import es.uca.iw.model.Contract;

@Service
public class BillService {
    private BillRepository billRepository;
    private ContractService contractService;
    
    public BillService(BillRepository billRepository, ContractService contractService) {
        this.billRepository = billRepository;
        this.contractService = contractService;
    }

    // El 1 de cada mes
    @Scheduled(cron = "0 0 0 1 * ?")
    public void generateBills() {
        List<Contract> contracts = contractService.getContracts();
        for (Contract contract : contracts) {
            Bill bill = new Bill();
            bill.setContract(contract);
            bill.setDate(this.contractService.getFirstDayOfMonth());
            
            bill.setdataConsumed(contractService.getDataConsumption(contract, getBillSearchingDate()));
            bill.setdataPrice(contract.getProduct().getDataUsagePrice());
            // bill.setminutesConsumed(contractService.getMinutesConsumption(contract));
            // bill.setminutesPrice(contract.getProduct().getMinutesUsagePrice());
            contract.getBills().add(bill);
            this.contractService.save(contract);
            this.billRepository.save(bill);
        }
    }

    // Buscamos gastos del mes anterior
    public Date getBillSearchingDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.add(Calendar.MONTH, -1);

        return calendar.getTime();
    }
}
