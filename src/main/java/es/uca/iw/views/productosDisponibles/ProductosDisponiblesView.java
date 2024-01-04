package es.uca.iw.views.productosDisponibles;

import jakarta.annotation.security.PermitAll;
import com.vaadin.flow.router.Route;

import java.util.List;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;

import es.uca.iw.services.ContractService;
import es.uca.iw.services.ProductService;
import es.uca.iw.model.Product;

@Route(value = "productosDisponibles")
@PermitAll
public class ProductosDisponiblesView extends VerticalLayout {
    
    private final ProductService productService;
    private final ContractService contractService;

    private List<Product> contractProducts;
    private List<Product> products;
    private OrderedList imageContainer;
    
    public ProductosDisponiblesView(ProductService productService, ContractService contractService) {
        this.productService = productService;
        this.contractService = contractService;
        
        this.products = this.productService.findAll();
        this.contractProducts = this.contractService.getContractProducts();

        constructUI();

        for (Product product : products) {
            if (product.getAvailable() && !contractProducts.contains(product))
                imageContainer.add(new ImageGalleryViewCard(contractService, product.getName(), product.getImage(), 
                    product.getDescription(), product.getPrice(), product.getDataPenaltyPrice(), product.getDataUsageLimit(),
                    product.getCallPenaltyPrice(), product.getCallLimit(), true));
        }
    }   

    private void constructUI() {
        addClassNames("image-gallery-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Productos disponibles");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.SMALL, FontSize.XXXLARGE);
        Paragraph description = new Paragraph("Aqui tiene todas las tarifas disponibles.");
        description.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        headerContainer.add(header, description);

        imageContainer = new OrderedList();
        imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);

        container.add(headerContainer);
        add(container, imageContainer);
    }
}
