package es.uca.iw.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
import es.uca.iw.model.Product;


@Service
public class BillService {
    private BillRepository billRepository;
    private ContractService contractService;
    private ProductService productService;
    
    public BillService(BillRepository billRepository, ContractService contractService, ProductService productService) {
        this.billRepository = billRepository;
        this.contractService = contractService;
        this.productService = productService;
    }

    // El 1 de cada mes
    @Scheduled(cron = "0 0 0 1 * ?")
    public void generateBills() {
        List<Contract> contracts = contractService.getAllContracts();
        for (Contract contract : contracts) {
            // Deberia de crear facturas del contrato mientras la fecha de actualizacion no corresponda con la que deberia ser
            if (contract.getLastBillUpdate() == null)
                contract.setLastBillUpdate(getBillSearchingDate(contract.getStartDate()));

            while (contract.getLastBillUpdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            .isBefore(getBillSearchingDate(new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
                System.err.println("SEARCHING DATE: " + getBillSearchingDate(new Date()));

                Bill bill = new Bill();
                bill.setContract(contract);
                bill.setDate(getNextBillSearchingDate(contract.getLastBillUpdate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                bill.setdataConsumed(0);
                bill.setminutesConsumed(0);

                // if (contract.getProduct().getProductType().contains(Product.ProductType.MOVIL))
                //     bill.setdataConsumed(contractService.getDataConsumption(contract, getBillSearchingDate()));
                
                // if (contract.getProduct().getProductType().contains(Product.ProductType.MOVIL) || 
                //     contract.getProduct().getProductType().contains(Product.ProductType.FIJO))
                //     bill.setminutesConsumed(contractService.getContractCallConsumption(contract, getBillSearchingDate()));
                if (this.productService.findByIdAndProductType(contract.getProduct().getId(), Product.ProductType.MOVIL).isPresent())
                    bill.setdataConsumed(contractService.getDataConsumption(contract, contract.getLastBillUpdate()));
                
                if (this.productService.findByIdAndProductType(contract.getProduct().getId(), Product.ProductType.MOVIL).isPresent() || 
                    this.productService.findByIdAndProductType(contract.getProduct().getId(), Product.ProductType.FIJO).isPresent())
                    bill.setminutesConsumed(contractService.getContractCallConsumption(contract, contract.getLastBillUpdate()));
                
                contract.getBills().add(bill);
                contract.setLastBillUpdate(getNextBillSearchingDate(contract.getLastBillUpdate()));
                this.contractService.save(contract);
                this.billRepository.save(bill);
            }
        }
    }

    // Search by last month
    // HAY QUE PONERLO PARA QUE SE PUEDA EJECUTAR CON LA FECHA QUE YO QUIERA
    public Date getBillSearchingDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND , 0);
        // calendar.add(Calendar.MONTH, -1);

        return calendar.getTime();
    }


    private Date getNextBillSearchingDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.add(Calendar.MONTH, 1);

        return calendar.getTime();
    }

    ////////////////////////////////////////////////
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Optional<Bill> getBillById(Long id) {
        return billRepository.findById(id);
    }

    public void deleteBill(Long id) {
        billRepository.deleteById(id);
    }


    public void saveBillWithDetails(String dataConsumedString, String minutesConsumedString,
    String dateString, String contractString) {
            Bill bill = new Bill();
            int dataConsumed = Integer.parseInt(dataConsumedString);
            bill.setdataConsumed(dataConsumed);
            int minutesConsumed = Integer.parseInt(minutesConsumedString);
            bill.setminutesConsumed(minutesConsumed);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(dateString, formatter);
            bill.setDate(date);
            

            bill.setContract(contractService.findById(Long.parseLong(contractString)).get());
            billRepository.save(bill);
        
    }


    public void editBillWithDetails(Long id, String newDataConsumed, String newMinutesConsumed, String newDate, String newContract) {
        Optional<Bill> optionalBill = billRepository.findById(id);
        if (optionalBill.isPresent()) {
            Bill bill = optionalBill.get();
            int dataConsumed = Integer.parseInt(newDataConsumed);
            bill.setdataConsumed(dataConsumed);
            int minutesConsumed = Integer.parseInt(newMinutesConsumed);
            bill.setminutesConsumed(minutesConsumed);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(newDate, formatter);
            bill.setDate(date);
 

            bill.setContract(contractService.findById(Long.parseLong(newContract)).get());
            billRepository.save(bill);
        }
        
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
        table.addCell(createCell("Precio total", font));
        table.addCell(createCell(bill.getTotalPrice().toString() + "€", font));

        return table;
    }

    private PdfPCell createCell(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setPadding(5);
        return cell;
    }
}
