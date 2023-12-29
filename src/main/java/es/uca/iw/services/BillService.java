package es.uca.iw.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;
import com.vaadin.flow.server.StreamResource;

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
            // bill.setminutesConsumed(contractService.getMinutesConsumption(contract));
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

    public StreamResource createPdfStreamResource(Bill bill) throws DocumentException {
        Document pdfBill = new Document();
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(pdfBill, pdfOutputStream);
            pdfBill.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk chunk = new Chunk("Hello World", font);

            pdfBill.add(chunk);
            pdfBill.close();

            StreamResource streamResource = new StreamResource(
                "Bill.pdf",
                () -> new ByteArrayInputStream(pdfOutputStream.toByteArray())
            );

            return streamResource;
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            if (pdfBill.isOpen())
                pdfBill.close();
        }

        return null;
    }
}

