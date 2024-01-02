package es.uca.iw.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.ZoneId;
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
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
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
        List<Contract> contracts = contractService.getAllContracts();
        for (Contract contract : contracts) {
            // Deberia de crear facturas del contrato mientras la fecha de actualizacion no corresponda con la que deberia ser
            if (contract.getLastBillUpdate() == null)
                contract.setLastBillUpdate(getBillSearchingDate());

            while (contract.getLastBillUpdate().before(getBillSearchingDate()) || contract.getLastBillUpdate().equals(getBillSearchingDate())) {
                Bill bill = new Bill();
                bill.setContract(contract);
                bill.setDate(getNextBillSearchingDate(contract.getLastBillUpdate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                
                bill.setdataConsumed(contractService.getDataConsumption(contract, getBillSearchingDate()));
                bill.setminutesConsumed(contractService.getContractCallConsumption(contract, getBillSearchingDate()));
                contract.getBills().add(bill);
                this.contractService.save(contract);
                this.billRepository.save(bill);

                contract.setLastBillUpdate(getNextBillSearchingDate(contract.getLastBillUpdate()));
            }
        }
    }

    // Search by last month
    public Date getBillSearchingDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.add(Calendar.MONTH, -1);

        return calendar.getTime();
    }

    private Date getNextBillSearchingDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.add(Calendar.MONTH, 1);

        return calendar.getTime();
    }

    public StreamResource createPdfStreamResource(Bill bill) throws DocumentException {
        Document pdfBill = new Document();
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(pdfBill, pdfOutputStream);
            pdfBill.open();
            Font font = FontFactory.getFont(FontFactory.TIMES, 12, BaseColor.BLACK);           

            pdfBill.add(new Paragraph(new Chunk("PhoneNet", font)));
            pdfBill.add(new Paragraph(new Chunk("Producto: " + bill.getContract().getProduct().getName(), font)));
            // Add space between title and table
            pdfBill.add(new Paragraph(new Chunk(" ")));
            pdfBill.add(createTable(bill, font));

            pdfBill.close();

            StreamResource streamResource = new StreamResource(
                bill.getContract().getProduct().getName() + bill.getDate() + ".pdf",
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

    private PdfPTable createTable(Bill bill, Font font) {
        PdfPTable table = new PdfPTable(2);

        table.addCell(createCell("Fecha", font));
        table.addCell(createCell(bill.getDate().toString(), font));
        table.addCell(createCell("Datos Consumidos", font));
        table.addCell(createCell(bill.getdataConsumed().toString() + "MB", font));
        table.addCell(createCell("Minutos Consumidos", font));
        table.addCell(createCell(bill.getminutesConsumed().toString() + "mins", font));
        table.addCell(createCell("Precio añadido por datos", font));
        table.addCell(createCell(bill.getDataTotalPrice().toString() + "€", font));
        table.addCell(createCell("Precio añadido por llamadas", font));
        table.addCell(createCell(bill.getCallTotalPrice().toString() + "€", font));

        return table;
    }

    private PdfPCell createCell(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setPadding(5);
        return cell;
    }
}

