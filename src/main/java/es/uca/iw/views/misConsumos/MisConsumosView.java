package es.uca.iw.views.misConsumos;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.PermitAll;
import com.vaadin.flow.component.html.Span;

@Route(value = "MisConsumos", layout = MainLayout.class)
@PermitAll
public class MisConsumosView extends VerticalLayout{
    public MisConsumosView() {
        add(new Span("Mis consumos"));
    }
}
