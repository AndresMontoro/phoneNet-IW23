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
        /*
        Example code using Grid component
        Grid<Person> grid = new Grid<>(Person.class, false);
        grid.addColumn(Person::getFirstName).setHeader("First name");
        grid.addColumn(Person::getLastName).setHeader("Last name");
        grid.addColumn(Person::getEmail).setHeader("Email");
        grid.addColumn(Person::getProfession).setHeader("Profession");

        List<Person> people = DataService.getPeople();
        grid.setItems(people);

        add(grid);
        */
        add(new Span("Mis facturas"));
    }
}
