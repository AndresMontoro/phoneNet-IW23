package es.uca.iw.views.Reclamaciones;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
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
import com.vaadin.flow.component.textfield.TextArea;

import es.uca.iw.services.ComplaintService;
import es.uca.iw.model.Complaint;
import es.uca.iw.views.MainUserLayout;
import jakarta.annotation.security.PermitAll;

import java.util.List;

@Route(value = "Reclamaciones", layout = MainUserLayout.class)
@PermitAll
public class ReclamacionesView extends VerticalLayout {

    private Grid<Complaint> grid;
    private ComplaintService complaintService;
    private Dialog dialog;

    public ReclamacionesView(ComplaintService complaintService) {
        this.complaintService = complaintService;
    
        addClassNames(Padding.MEDIUM, Display.FLEX, FlexDirection.COLUMN, Gap.LARGE,Horizontal.AUTO, Vertical.AUTO);

        H2 header = new H2("MIS RECLAMACIONES");
        header.addClassNames(Horizontal.AUTO, FontSize.XXLARGE, Background.CONTRAST_5, BorderRadius.MEDIUM, Padding.MEDIUM, Margin.SMALL);
        header.getStyle().set("color", "blue");
        add(header);

        // Creamos la tabla
        grid = new Grid<>(Complaint.class);
        grid.setColumns("id", "description", "creationDate", "status", "comments");
        
        // Modificamos los nombres de la cabecera
        grid.getColumnByKey("id").setHeader("ID");
        grid.getColumnByKey("description").setHeader("Descripción");
        grid.getColumnByKey("creationDate").setHeader("Fecha de Creación");
        grid.getColumnByKey("status").setHeader("Estado");
        grid.getColumnByKey("comments").setHeader("Comentarios");

        // Añadir una columna de botón para acciones
        grid.addComponentColumn(complaint -> {

            Button accionesButton = new Button("Acciones");
            accionesButton.addClickListener(e -> mostrarAcciones(complaint));
            return accionesButton;

        }).setHeader("Acciones");
    
        // Botón para añadir reclamaciones (con funcionalidad)
        Button añadirReclamacionButton = new Button("Añadir Reclamación");
        añadirReclamacionButton.addClickListener(e -> mostrarFormularioAñadirReclamacion());
    
        add(añadirReclamacionButton);
        add(grid);
    
        // Actualizar los datos de la tabla de reclamaciones
        actualizarTablaReclamaciones();
    }

    // Método para mostrar las acciones de una reclamación
    private void mostrarAcciones(Complaint complaint) {
        // Crear un cuadro de diálogo para mostrar las acciones
        dialog = new Dialog();
        dialog.setCloseOnOutsideClick(false);

        // Crear un formulario para añadir o eliminar comentarios
        FormLayout formLayout = new FormLayout();


        Button opcion1Button = new Button("Eliminar Reclamación");
        opcion1Button.addClickListener(e -> eliminarReclamacion(complaint));


        Button opcion2Button = new Button("Añadir Comentarios");
        opcion2Button.addClickListener(e -> {
            // Llamada al servicio para añadir comentarios a la reclamación
            mostrarFormularioAñadirComentarios(complaint);
            // Cerrar el cuadro de diálogo principal
            dialog.close();
        });

        formLayout.add(opcion1Button, opcion2Button);
        dialog.add(formLayout);

        dialog.open();
    }

    // Método para eliminar una reclamación
    private void eliminarReclamacion(Complaint complaint) {
        // Llamar al servicio para eliminar la reclamación
        complaintService.eliminarReclamacion(complaint.getId());

        // Actualizar la tabla de reclamaciones
        actualizarTablaReclamaciones();
        
        dialog.close();
    }

    // Método para mostrar el formulario de añadir comentarios
    private void mostrarFormularioAñadirComentarios(Complaint complaint) {
        // Crear un cuadro de diálogo para mostrar el formulario de añadir comentarios
        Dialog comentariosDialog = new Dialog();
        comentariosDialog.setCloseOnOutsideClick(false);

        // Crear un formulario para añadir comentarios
        FormLayout comentariosFormLayout = new FormLayout();

        // Campos del formulario
        Span comentariosLabel = new Span("Comentarios");
        TextArea comentariosField = new TextArea();
        comentariosField.setWidthFull();

        // Botón para confirmar la adición de comentarios
        Button confirmarComentariosButton = new Button("Confirmar");
        confirmarComentariosButton.addClickListener(e -> {
            // Llamada al servicio para añadir comentarios a la reclamación
            añadirComentariosAReclamacion(complaint, comentariosField.getValue());
            // Actualizar la tabla
            actualizarTablaReclamaciones();
            // Cerrar el diálogo de comentarios
            comentariosDialog.close();
        });

        comentariosFormLayout.add(comentariosLabel, comentariosField, confirmarComentariosButton);
        comentariosDialog.add(comentariosFormLayout);

        // Abrir el cuadro de diálogo de comentarios
        comentariosDialog.open();
    }

    // Método para añadir comentarios a una reclamación
    private void añadirComentariosAReclamacion(Complaint complaint, String comentarios) {
        // Llamada al servicio para añadir comentarios a la reclamación
        complaintService.addComentariosAReclamacion(complaint, comentarios);
    }

    // Método para mostrar el formulario de añadir reclamación
    private void mostrarFormularioAñadirReclamacion() {
        
        // Crear un cuadro de diálogo para mostrar el formulario de añadir reclamación
        Dialog dialog = new Dialog();
        dialog.setCloseOnOutsideClick(false);

        // Crear un formulario para añadir reclamación
        FormLayout formLayout = new FormLayout();

        // Campos del formulario
        Span descripcionLabel = new Span("Descripción");
        TextArea descripcionField = new TextArea();
        descripcionField.setWidthFull();

        Span comentariosLabel = new Span("Comentarios");
        TextArea comentariosField = new TextArea();
        comentariosField.setWidthFull();

        // Botón para confirmar la adición
        Button confirmarButton = new Button("Confirmar");
        confirmarButton.addClickListener(e -> {
      
            Complaint nuevaReclamacion = new Complaint();

            nuevaReclamacion.setDescription(descripcionField.getValue());
            nuevaReclamacion.setComments(comentariosField.getValue());

            // Llamada al servicio para añadir la reclamación
            complaintService.addComplaint(nuevaReclamacion);

            // Actualizar la tabla
            actualizarTablaReclamaciones();

            // Cerrar el diálogo
            dialog.close();
        });

        formLayout.add(descripcionLabel, descripcionField, comentariosLabel, comentariosField, confirmarButton);
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