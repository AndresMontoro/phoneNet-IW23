package es.uca.iw.views.landingPage;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
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
import com.vaadin.flow.theme.lumo.LumoUtility.Width;

@Route(value = "")
@AnonymousAllowed
public class LandingPageView extends VerticalLayout {

    public LandingPageView() {
        setMinHeight("100vh");
        addClassNames(Padding.MEDIUM, Display.FLEX, FlexDirection.COLUMN, Gap.LARGE,Horizontal.AUTO, Vertical.AUTO);

        H2 header = new H2("BIENVENIDO A PHONENET");
        header.addClassNames(Horizontal.AUTO, FontSize.XXLARGE, Background.CONTRAST_5, BorderRadius.MEDIUM, Padding.MEDIUM, Margin.SMALL);
        header.getStyle().set("color", "blue");

        Button accessToUserPage = new Button("Área de clientes");
        accessToUserPage.addClickListener(e -> accessToUserPage.getUI().ifPresent(ui -> ui.navigate("user/MiPerfil")));
        Button accessToAdminPage = new Button("Área de empleados");
        accessToAdminPage.addClickListener(e -> accessToAdminPage.getUI().ifPresent(ui -> ui.navigate("admin/EditarContratos")));

        VerticalLayout container = new VerticalLayout();
        container.addClassNames(Background.CONTRAST_5, BorderRadius.MEDIUM, Padding.MEDIUM, Vertical.AUTO, Horizontal.AUTO, Width.SMALL, Margin.XLARGE);
        
        HorizontalLayout buttonsContainer = new HorizontalLayout();
        buttonsContainer.add(accessToUserPage, accessToAdminPage);
        buttonsContainer.addClassNames(Horizontal.AUTO, Margin.SMALL);

        Button accessToRegisterPage = new Button("¿Aún no tienes un cuenta?");
        accessToRegisterPage.addClickListener(e -> accessToRegisterPage.getUI().ifPresent(ui -> ui.navigate("Registro")));
        accessToRegisterPage.addClassNames(Horizontal.AUTO, Margin.SMALL);

        container.add(header, buttonsContainer, accessToRegisterPage);

        add(container);
    }
}
