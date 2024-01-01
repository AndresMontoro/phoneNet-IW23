package es.uca.iw.views.misFacturas;

import java.util.List;
import java.util.Set;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
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
        grid.setColumns("date", "dataConsumed", "minutesConsumed", "totalPrice");
        grid.getColumnByKey("date").setHeader("Fecha");
        grid.getColumnByKey("dataConsumed").setHeader("Datos Consumidos");
        grid.getColumnByKey("minutesConsumed").setHeader("Minutos Consumidos");
        grid.getColumnByKey("totalPrice").setHeader("Precio Total");
        grid.addComponentColumn(bill -> createInfoButton(bill)).setHeader("Más información");
        add(grid);
    }

    private Button createInfoButton(Bill bill) {
        Button button = new Button("Descargar");
        button.addClickListener(event -> {
            // Lógica para manejar el clic en el botón (editar en este caso)
            // Puedes abrir un formulario de edición o realizar otras acciones según tus necesidades
        });
        return button;
    }
}
