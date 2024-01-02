package es.uca.iw.views.misProductos;

import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin.Horizontal;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin.Vertical;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;

import es.uca.iw.services.ContractService;
import es.uca.iw.model.Product;
import es.uca.iw.views.MainLayout;
import es.uca.iw.views.productosDisponibles.ImageGalleryViewCard;
import jakarta.annotation.security.PermitAll;

@Route(value = "MisProductos", layout = MainLayout.class)
@PermitAll
public class MisProductosView extends VerticalLayout{
    private ContractService contractService;

    public MisProductosView(ContractService contractService) {
        this.contractService = contractService;

        addClassNames(Padding.MEDIUM, Display.FLEX, FlexDirection.COLUMN, Gap.LARGE,Horizontal.AUTO, Vertical.AUTO);

        H2 header = new H2("PRODUCTOS CONTRATADOS");
        header.addClassNames(Horizontal.AUTO, FontSize.XXLARGE, Background.CONTRAST_5, BorderRadius.MEDIUM, Padding.MEDIUM, Margin.SMALL);
        header.getStyle().set("color", "blue");
        add(header);

        List<Product> actualUserProducts = this.contractService.getContractProducts();
        
        if (actualUserProducts.size() != 0) {
            for (Product product : actualUserProducts) {
                add(new ImageGalleryViewCard(contractService, product.getName(), product.getImage(), product.getDescription(), product.getPrice(), 
                    product.getDataUsagePrice(), product.getDataUsageLimit(), product.getCallPrice(), product.getCallLimit(), false));
            }
        } else {
            add(new H2("No tienes productos contratados"));
        }

        Button seeMoreButton = new Button("Catálogo de productos");
        seeMoreButton.addClickListener(event -> {
            seeMoreButton.getUI().ifPresent(ui -> ui.navigate("productosDisponibles"));
        });

        add(seeMoreButton);
    }
}
