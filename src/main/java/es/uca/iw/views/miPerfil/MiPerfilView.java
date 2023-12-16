package es.uca.iw.views.miPerfil;

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

import es.uca.iw.model.User;
import es.uca.iw.services.UserDetailsServiceImpl;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@Route(value = "", layout = MainLayout.class)
@PermitAll
public class MiPerfilView extends VerticalLayout {

    private UserDetailsServiceImpl userService;

    public MiPerfilView(UserDetailsServiceImpl userService) {
        this.userService = userService;
        addClassNames(Padding.MEDIUM, Display.FLEX, FlexDirection.COLUMN, Gap.LARGE,Horizontal.AUTO, Vertical.AUTO);
    
        H2 header = new H2("MI PERFIL");
        header.addClassNames(Horizontal.AUTO, FontSize.XXLARGE, Background.CONTRAST_5, BorderRadius.MEDIUM, Padding.MEDIUM, Margin.SMALL);
        header.getStyle().set("color", "blue");
        add(header);

        // Ahora es un usuario de prueba, en un futuro sera el usuario que inicie sesion
        User actualUser = this.userService.findByUsername("andresmontoro").orElse(null);

        if (actualUser != null) {
            // Cuadro de informacion del usuario
            VerticalLayout userInfo = new VerticalLayout();
            userInfo.addClassNames(Background.CONTRAST_10, BorderRadius.MEDIUM, Padding.MEDIUM, Margin.SMALL);
            userInfo.getStyle().set("color", "blue");
            userInfo.add(new Span("Nombre: " + actualUser.getName()));
            userInfo.add(new Span("Apellidos: " + actualUser.getSurname()));

            add (userInfo);
        }
    }
}
