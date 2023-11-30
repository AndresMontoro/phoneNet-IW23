package es.uca.iw.views.misProductos;

import java.util.Set;

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

import es.uca.iw.services.ProductService;
import es.uca.iw.services.UserService;
import es.uca.iw.model.User;
import es.uca.iw.model.Product;
import es.uca.iw.views.MainLayout;
import es.uca.iw.views.productosDisponibles.ImageGalleryViewCard;

@Route(value = "MisProductos", layout = MainLayout.class)
// @RolesAllowed(value = "USER")
public class MisProductosView extends VerticalLayout{
    private UserService userService;
    private ProductService productService;

    public MisProductosView(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;

        addClassNames(Padding.MEDIUM, Display.FLEX, FlexDirection.COLUMN, Gap.LARGE,Horizontal.AUTO, Vertical.AUTO);
    
        H2 header = new H2("PRODUCTOS CONTRATADOS");
        header.addClassNames(Horizontal.AUTO, FontSize.XXLARGE, Background.CONTRAST_5, BorderRadius.MEDIUM, Padding.MEDIUM, Margin.SMALL);
        header.getStyle().set("color", "blue");

        add(header);

        // Ahora es un usuario de prueba, en un futuro sera el usuario que inicie sesion
        User actualUser = this.userService.findByUsername("andresmontoro").orElse(null); 
        Set<Product> actualUserProducts = this.userService.getUserProducts(actualUser);
        if (actualUserProducts.size() != 0) {
            for (Product product : actualUserProducts) {
                add(new ImageGalleryViewCard(productService, product.getName(), product.getImage(), product.getDescription(), product.getPrice(), false));
            }
        }

        Button seeMoreButton = new Button("Ver más");
        seeMoreButton.addClickListener(event -> {
            seeMoreButton.getUI().ifPresent(ui -> ui.navigate("productosDisponibles"));
        });

        add(seeMoreButton);
    }
}
