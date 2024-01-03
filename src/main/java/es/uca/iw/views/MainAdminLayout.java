package es.uca.iw.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;

import es.uca.iw.views.editarContratos.EditarContratosView;
import es.uca.iw.views.editarProductos.EditarProductosView;
import es.uca.iw.views.editarUsuarios.EditarUsuariosView;
import es.uca.iw.views.login.LoginView;

public class MainAdminLayout extends AppLayout{
    public MainAdminLayout() {
        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("PhoneNet");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)").set("margin", "0");

        Tabs tabs = getTabs();
        Button logoutButton = new Button("Cerrar Sesión", VaadinIcon.SIGN_OUT.create());
        logoutButton.addClickListener(e -> logout());

        Div titleDiv = new Div(title);
        Div logoutButtonDiv = new Div(logoutButton);
        logoutButtonDiv.getStyle().set("margin-left", "auto");

        addToDrawer(tabs);
        addToNavbar(toggle, titleDiv, logoutButtonDiv);
    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();
        tabs.add(createTab(VaadinIcon.CLIPBOARD, "Editar Productos", EditarProductosView.class),
            createTab(VaadinIcon.CLIPBOARD, "Editar Usuarios", EditarUsuariosView.class),
            createTab(VaadinIcon.CLIPBOARD, "Editar Contratos", EditarContratosView.class) 
        );

        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        return tabs;
    }

    private Tab createTab(VaadinIcon viewIcon, String viewName, Class<? extends Component> viewClass) {
        Icon icon = viewIcon.create();
        icon.getStyle().set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("margin-inline-start", "var(--lumo-space-xs)")
                .set("padding", "var(--lumo-space-xs)");

        RouterLink link = new RouterLink();
        link.add(icon, new Span(viewName));
        // Demo has no routes
        link.setRoute(viewClass);
        link.setTabIndex(-1);

        return new Tab(link);
    }

    private void logout() {
        VaadinSession.getCurrent().getSession().invalidate();
        getUI().ifPresent(ui -> ui.navigate(LoginView.class));
    }
}