package es.uca.iw.views.misFacturas;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@Route(value = "MisFacturas", layout = MainLayout.class)
@PermitAll
public class MisFacturasView extends VerticalLayout{
    public MisFacturasView() {
        add(new Span("Mis facturas"));
    }
}
