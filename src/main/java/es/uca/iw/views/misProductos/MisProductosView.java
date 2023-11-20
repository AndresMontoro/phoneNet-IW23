package es.uca.iw.views.misProductos;

import java.math.BigDecimal;

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

import es.uca.iw.views.MainLayout;
import es.uca.iw.views.productosDisponibles.ImageGalleryViewCard;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "MisProductos", layout = MainLayout.class)
@RolesAllowed(value = "USER")
public class MisProductosView extends VerticalLayout{
    public MisProductosView() {
        addClassNames(Padding.MEDIUM, Display.FLEX, FlexDirection.COLUMN, Gap.LARGE,Horizontal.AUTO, Vertical.AUTO);
    
        H2 header = new H2("PRODUCTOS CONTRATADOS");
        header.addClassNames(Horizontal.AUTO, FontSize.XXLARGE, Background.CONTRAST_5, BorderRadius.MEDIUM, Padding.MEDIUM, Margin.SMALL);
        header.getStyle().set("color", "blue");


        ImageGalleryViewCard image = new ImageGalleryViewCard("Tarifa Hola Mundo", "https://images.unsplash.com/photo-1519681393784-d120267933ba?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw", 
            "Tarifa todo en uno para programadores. Garantiza una experiencia de navegación de 600Mb simétricos, llamadas ilimitadas en tu fijo y móvil y 20GB de datos móviles.", new BigDecimal(30.0), false);

        Button seeMoreButton = new Button("Ver más");
        seeMoreButton.addClickListener(event -> {
            seeMoreButton.getUI().ifPresent(ui -> ui.navigate("productosDisponibles"));
        });

        add(header, image, seeMoreButton);
    }
}
