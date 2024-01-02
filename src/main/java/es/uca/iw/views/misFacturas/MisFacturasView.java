package es.uca.iw.views.misFacturas;

import java.util.List;
import java.util.Set;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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
        grid.setColumns("date", "dataConsumed", "dataTotalPrice", "minutesConsumed", "callTotalPrice","totalPrice");
        grid.getColumnByKey("date").setHeader("Fecha");
        grid.getColumnByKey("dataConsumed").setHeader("Datos Consumidos");
        grid.getColumnByKey("dataTotalPrice").setHeader("Precio Añadido (Datos)");
        grid.getColumnByKey("minutesConsumed").setHeader("Minutos Consumidos");
        grid.getColumnByKey("callTotalPrice").setHeader("Precio añadido (Llamadas)");
        grid.getColumnByKey("totalPrice").setHeader("Precio Total");
        
        grid.addComponentColumn(bill -> createDownloadAnchor(bill)).setHeader("Descargar");
        add(grid);
    }

    private Anchor createDownloadAnchor(Bill bill) {
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
