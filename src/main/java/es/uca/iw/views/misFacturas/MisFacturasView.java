package es.uca.iw.views.misFacturas;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

import org.springframework.web.servlet.tags.form.ButtonTag;
import org.vaadin.olli.FileDownloadWrapper;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin.Horizontal;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin.Vertical;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;

import es.uca.iw.model.Bill;
import es.uca.iw.model.Contract;
import es.uca.iw.services.BillService;
import es.uca.iw.services.ContractService;
import es.uca.iw.views.MainUserLayout;
import jakarta.annotation.security.PermitAll;

@Route(value = "MisFacturas", layout = MainUserLayout.class)
@PermitAll
public class MisFacturasView extends VerticalLayout {
    private ContractService contractService;
    private BillService billService;

    public MisFacturasView(ContractService contractService, BillService billService) {
        this.contractService = contractService;
        this.billService = billService;

        addClassNames(Padding.MEDIUM, Display.FLEX, FlexDirection.COLUMN, Gap.LARGE,Horizontal.AUTO, Vertical.AUTO);

        H2 header = new H2("MIS FACTURAS");
        header.addClassNames(Horizontal.AUTO, FontSize.XXLARGE, Background.CONTRAST_5, BorderRadius.MEDIUM, Padding.MEDIUM, Margin.SMALL);
        header.getStyle().set("color", "blue");
        add(header);

        List<Contract> contracts = this.contractService.getContracts();
        for (Contract contract : contracts) {
            add(new H2("Facturas de la tarifa:  " + contract.getProduct().getName()));
            constructBillUI(this.contractService, this.billService, contract);
        }
    }

    public void constructBillUI(ContractService contractService, BillService billService, Contract contract) {
        Set<Bill> bills = contract.getBills();

        Grid<Bill> grid = new Grid<>(Bill.class, false);
        grid.setItems(bills);
        grid.setColumns("date", "dataConsumed", "minutesConsumed", "dataTotalPrice", "totalPrice");
        grid.getColumnByKey("date").setHeader("Fecha");
        grid.getColumnByKey("dataConsumed").setHeader("Datos Consumidos");
        grid.getColumnByKey("minutesConsumed").setHeader("Minutos Consumidos");
        grid.getColumnByKey("totalPrice").setHeader("Precio Total");
        grid.getColumnByKey("dataTotalPrice").setHeader("Precio Añadido (Datos)");
        grid.addComponentColumn(bill -> createDownloadAnchor(bill)).setHeader("Más información");
        add(grid);
    }

    private Anchor createDownloadAnchor(Bill bill) {
        // try {
        //     ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        //     StreamResource streamResource = billService.createPdfStreamResource(bill, pdfOutputStream);
    
        //     // Crear un componente Anchor para la descarga
        //     Anchor downloadAnchor = new Anchor(streamResource, "Descargar");
        //     downloadAnchor.getElement().getThemeList().add("button");
        //     downloadAnchor.getElement().setAttribute("download", true);
    
        //     // Manejar el clic en el Anchor
        //     // downloadAnchor.addClickListener(event -> {
        //     //     // Agregar lógica adicional si es necesario
        //     //     Notification.show("Descargando factura...");
        //     // });
    
        //     return downloadAnchor;
        // } catch (Exception e) {
        //     Notification.show("Debido a un error, no se pueden descargar las facturas");
        //     return new Anchor(); // Puedes devolver un Anchor vacío o manejarlo de otra manera
        // }

        try {
            StreamResource streamResource = billService.createPdfStreamResource(bill);
            Anchor link = new Anchor(streamResource, "");
            link.getElement().setAttribute("download", true);
            link.add(new Button(new Icon(VaadinIcon.DOWNLOAD_ALT)));
            return link;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    
}
