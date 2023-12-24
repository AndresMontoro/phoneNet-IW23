package es.uca.iw.views.Reclamaciones;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import es.uca.iw.services.ComplaintService;
import es.uca.iw.model.Complaint;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "Reclamaciones", layout = MainLayout.class)
@PermitAll
public class ReclamacionesView extends VerticalLayout {

    private Grid<Complaint> grid;
    private ComplaintService complaintService;

    @Autowired
    public ReclamacionesView(ComplaintService complaintService) {
        this.complaintService = complaintService;
    
        // Crear una tabla para mostrar las reclamaciones
        grid = new Grid<>(Complaint.class); 
    
        // Agregar las columnas
        //grid.addColumn(Complaint::getId).setHeader("ID");
        //grid.addColumn(Complaint::getDescription).setHeader("Descripción");
        //grid.addColumn(Complaint::getCreationDate).setHeader("Fecha de Creación");
        //grid.addColumn(Complaint::getStatus).setHeader("Estado");
        //grid.addColumn(Complaint::getComments).setHeader("Comentarios");
    
        // Personalizar la visualización de la fecha
        grid.getColumnByKey("creationDate").setHeader("Fecha de Creación");
    
        // Añadir una columna de botón para acciones
        grid.addComponentColumn(complaint -> {
            Button accionesButton = new Button("Acciones");
            accionesButton.addClickListener(e -> mostrarAcciones(complaint));
            return accionesButton;
        }).setHeader("Acciones");
    
        // Botón para añadir reclamaciones (sin funcionalidad por ahora)
        Button añadirReclamacionButton = new Button("Añadir Reclamación");
        añadirReclamacionButton.addClickListener(e -> mostrarFormularioAñadirReclamacion());
    
        add(new Span("Reclamaciones"));
        add(añadirReclamacionButton);
        add(grid);
    
        // Actualizar los datos de la tabla de reclamaciones
        actualizarTablaReclamaciones();
    }

    // Método para mostrar las acciones de una reclamación
    private void mostrarAcciones(Complaint complaint) {
        // Crear un cuadro de diálogo para mostrar las acciones
        Dialog dialog = new Dialog();
        dialog.setCloseOnOutsideClick(false);

        // Crear un formulario para añadir o eliminar comentarios
        FormLayout formLayout = new FormLayout();

        // Añadir dos botones vacíos
        Button opcion1Button = new Button("Eliminar Reclamación");
        Button opcion2Button = new Button("Añadir Comentarios");

        formLayout.add(opcion1Button, opcion2Button);
        dialog.add(formLayout);

        // Abrir el cuadro de diálogo
        dialog.open();
    }

    // Método para mostrar el formulario de añadir reclamación (sin funcionalidad por ahora)
    private void mostrarFormularioAñadirReclamacion() {
        // Crear un cuadro de diálogo para mostrar el formulario de añadir reclamación
        Dialog dialog = new Dialog();
        dialog.setCloseOnOutsideClick(false);

        // Crear un formulario para añadir reclamación
        FormLayout formLayout = new FormLayout();

        // Campos del formulario (sin funcionalidad por ahora)
        formLayout.add(new Span("Formulario para añadir reclamación"));

        dialog.add(formLayout);

        // Abrir el cuadro de diálogo
        dialog.open();
    }

    // Método para actualizar los datos de la tabla de reclamaciones
    private void actualizarTablaReclamaciones() {
        List<Complaint> reclamaciones = complaintService.findAll();
        grid.setItems(reclamaciones);
    }
}