package es.uca.iw.views.misConsumos;

import java.util.List;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
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

import es.uca.iw.model.Contract;
import es.uca.iw.model.Product;
import es.uca.iw.services.ContractService;
import es.uca.iw.services.ProductService;
import es.uca.iw.views.MainUserLayout;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "user/MisConsumos", layout = MainUserLayout.class)
@RolesAllowed({"ADMIN", "USER"})
public class MisConsumosView extends VerticalLayout {

    private ContractService contractService;
    private ProductService productService;

    public MisConsumosView(ContractService contractService, ProductService productService) {
        this.contractService = contractService;
        this.productService = productService;

        addClassNames(Padding.MEDIUM, Display.FLEX, FlexDirection.COLUMN, Gap.LARGE,Horizontal.AUTO, Vertical.AUTO);

        H2 header = new H2("CONSUMOS EN LOS PRODUCTOS CONTRATADOS");
        header.addClassNames(Horizontal.AUTO, FontSize.XXLARGE, Background.CONTRAST_5, BorderRadius.MEDIUM, Padding.MEDIUM, Margin.SMALL);
        header.getStyle().set("color", "blue");
        add(header);

        List<Contract> actualUserContracts = contractService.getContracts();

        for (Contract contract : actualUserContracts) {
            Boolean isFibra = this.productService.findByIdAndProductType(contract.getProduct().getId(), Product.ProductType.FIBRA).isPresent();
            Boolean isMovil = this.productService.findByIdAndProductType(contract.getProduct().getId(), Product.ProductType.MOVIL).isPresent();
            Boolean isFijo = this.productService.findByIdAndProductType(contract.getProduct().getId(), Product.ProductType.FIJO).isPresent();

            add(new H2("Tarifa: " + contract.getProduct().getName()));
            if(isMovil)
                add(new H3("Consumo de datos: " + this.contractService.getDataConsumption(contract, this.contractService.getFirstDayOfMonth()) + " MB de " 
                    + contract.getProduct().getDataUsageLimit() + " MB"));
            if(isMovil || isFijo)
                add(new H3("Consumo de llamadas: " + this.contractService.getContractCallConsumption(contract, this.contractService.getFirstDayOfMonth()) 
                    + " minutos de " + contract.getProduct().getCallLimit() + " minutos"));
            if(isFibra && !isMovil && !isFijo)
                add(new H3("Este contrato no tiene consumos asociados"));
        }
    }
}
