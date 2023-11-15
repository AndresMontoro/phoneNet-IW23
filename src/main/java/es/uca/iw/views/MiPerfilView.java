package es.uca.iw.views;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "", layout = MainLayout.class)
@RolesAllowed(value = "USER")
public class MiPerfilView extends VerticalLayout{
    public MiPerfilView() {
        add(new Span("Mi perfil"));
    }
}
