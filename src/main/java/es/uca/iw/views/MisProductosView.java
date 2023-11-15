package es.uca.iw.views;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "MisProductos", layout = MainLayout.class)
@RolesAllowed(value = "USER")
public class MisProductosView extends VerticalLayout{
    public MisProductosView() {
        add(new Span("Mis productos"));
    }
}
