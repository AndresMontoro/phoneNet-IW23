package es.uca.iw.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;

import es.uca.iw.views.miPerfil.MiPerfilView;
import es.uca.iw.views.misConsumos.MisConsumosView;
import es.uca.iw.views.misFacturas.MisFacturasView;
import es.uca.iw.views.misProductos.MisProductosView;
import es.uca.iw.views.editarProductos.EditarProductosView;

public class MainLayout extends AppLayout{
    public MainLayout() {
        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("PhoneNet");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        Tabs tabs = getTabs();

        addToDrawer(tabs);
        addToNavbar(toggle, title);
    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();
        tabs.add(createTab(VaadinIcon.USER_HEART, "Mi perfil", MiPerfilView.class),
            createTab(VaadinIcon.CART, "Mis productos", MisProductosView.class),
            createTab(VaadinIcon.CHART, "Mis Consumos", MisConsumosView.class),
            createTab(VaadinIcon.CLIPBOARD, "Mis facturas", MisFacturasView.class),
            createTab(VaadinIcon.CLIPBOARD, "Editar Productos", EditarProductosView.class));
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
}
