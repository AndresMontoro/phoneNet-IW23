package es.uca.iw.views.productosDisponibles;

import java.math.BigDecimal;
import java.util.Set;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;

import es.uca.iw.model.Product;
import es.uca.iw.services.ContractService;
import es.uca.iw.services.ProductService;

public class ImageGalleryViewCard extends ListItem {
    private ContractService contractService;
    private ProductService productService;

    public ImageGalleryViewCard(ProductService productService, ContractService contractService, String productName, String productUrl, String productDescription, 
        BigDecimal productPrice, BigDecimal dataPrice, int dataUsageLimit, BigDecimal callPrice, int callLimit, boolean hireVisible, int routerSpeed, Long id) {

        this.contractService = contractService;
        this.productService = productService;

        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.SMALL, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        div.setHeight("250px");
        div.getStyle().set("width", "100%");

        Image image = new Image();
        image.setWidth("100%");
        image.setSrc(productUrl);
        image.setAlt(productDescription);

        div.add(image);
        add(div);

        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(productName);
        add(header);

        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle.setText(productPrice.toString() + "€/mes");
        add(subtitle);

        Set<Product.ProductType> productTypes = this.productService.getProductTypes(id);

        if (productTypes.contains(Product.ProductType.MOVIL)) {
            Span dataUsage = new Span("Datos: " + dataUsageLimit  / 1024 + "GB" + " - " + dataPrice.toString() + "€/MB");
            dataUsage.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
            add(dataUsage);
        }
        
        if (productTypes.contains(Product.ProductType.MOVIL) || productTypes.contains(Product.ProductType.FIJO)) {
            Span callUsage = new Span("Llamadas: " + callLimit + "min" + " - " + callPrice.toString() + "€/min");
            callUsage.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
            add(callUsage);
        }
        
        if (productTypes.contains(Product.ProductType.FIBRA)) {
            Span rSpeed = new Span("Velocidad del router: " + routerSpeed + "Mbps");
            rSpeed.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
            add(rSpeed);
        }

        Paragraph description = new Paragraph(productDescription);
        description.addClassName(Margin.Vertical.MEDIUM);
        add(description);

        if (hireVisible) {
            Button badge = new Button("Contratarlo");
            badge.addClickListener(event -> {
                try {
                    contractService.hireProduct(productName);
                    badge.getUI().ifPresent(ui -> ui.navigate("/user/MisProductos"));
                    Notification.show("Producto contratado correctamente");
                } catch (Exception e) {
                    Notification.show("No se ha podido contratar el producto. " + e.getMessage());
                }
            });
            add(badge);
        } else {
            Button badgeUnhire = new Button("Descontratarlo");
            badgeUnhire.addClickListener(event -> {
                try {
                    this.contractService.unhireProduct(productName);
                    getElement().executeJs("location.reload()");
                    Notification.show("Producto descontratado correctamente");
                } catch (Exception e) {
                    Notification.show("No se ha podido descontratar el producto. " + e.getMessage());
                }
            });
            add(badgeUnhire);
        }
    }
}
