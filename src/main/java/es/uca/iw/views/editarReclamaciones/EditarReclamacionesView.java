package es.uca.iw.views.editarReclamaciones;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
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
import es.uca.iw.views.MainAdminLayout;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;
import java.util.stream.Collectors;

@Route(value = "admin/Reclamaciones", layout = MainAdminLayout.class)
@RolesAllowed("ADMIN")
public class EditarReclamacionesView extends VerticalLayout {

    private Grid<Complaint> grid;
    private ComplaintService complaintService;
    private Dialog dialog;

    public EditarReclamacionesView(ComplaintService complaintService) {
        this.complaintService = complaintService;

        addClassNames(Padding.MEDIUM, Display.FLEX, FlexDirection.COLUMN, Gap.LARGE, Horizontal.AUTO, Vertical.AUTO);

        H2 header = new H2("RECLAMACIONES ACTIVAS");
        header.addClassNames(Horizontal.AUTO, FontSize.XXLARGE, Background.CONTRAST_5, BorderRadius.MEDIUM, Padding.MEDIUM, Margin.SMALL);
        header.getStyle().set("color", "blue");
        add(header);

        grid = new Grid<>(Complaint.class);
        grid.setColumns("id", "userId", "description", "creationDate", "status", "comments");
        grid.getColumnByKey("id").setHeader("ID").setWidth("40px"); 
        grid.getColumnByKey("userId").setHeader("Usuario").setWidth("40px");
        grid.getColumnByKey("description").setHeader("Descripción");
        grid.getColumnByKey("creationDate").setHeader("Fecha de Creación");
        grid.getColumnByKey("status").setHeader("Estado");
        grid.getColumnByKey("comments").setHeader("Comentarios");

        grid.addComponentColumn(complaint -> {
            Button accionesButton = new Button("Atender Reclamación");
            accionesButton.addClickListener(e -> atenderReclamacion(complaint));
            return accionesButton;
        }).setHeader("Acciones");

        add(grid);

        actualizarTablaReclamacionesActivas();
    }

    private void atenderReclamacion(Complaint complaint) {
        dialog = new Dialog();
        dialog.setCloseOnOutsideClick(false);

        FormLayout formLayout = new FormLayout();

        Button cambiarEstadoButton = new Button("Cambiar Estado");
        cambiarEstadoButton.addClickListener(e -> mostrarOpcionesCambioEstado(complaint));

        Button eliminarReclamacionButton = new Button("Eliminar Reclamación");
        eliminarReclamacionButton.addClickListener(e -> eliminarReclamacion(complaint));

        Button añadirComentariosButton = new Button("Añadir Comentarios");
        añadirComentariosButton.addClickListener(e -> mostrarFormularioAñadirComentarios(complaint));

        Button verComentariosButton = new Button("Ver Comentarios");
        verComentariosButton.addClickListener(e -> mostrarComentarios(complaint));

        formLayout.add(cambiarEstadoButton, eliminarReclamacionButton, añadirComentariosButton, verComentariosButton);
        dialog.add(formLayout);

        dialog.open();
    }

    private void eliminarReclamacion(Complaint complaint) {
        complaintService.eliminarReclamacion(complaint.getId());
        actualizarTablaReclamacionesActivas();
        dialog.close();
    }

    private void addComentariosAReclamacion(Complaint complaint, String comentarios) {
        complaintService.addComentariosAReclamacion(complaint, comentarios);
    }

    private void mostrarFormularioAñadirComentarios(Complaint complaint) {
        Dialog comentariosDialog = new Dialog();
        comentariosDialog.setCloseOnOutsideClick(false);

        FormLayout comentariosFormLayout = new FormLayout();

        Span comentariosLabel = new Span("Comentarios");
        TextArea comentariosField = new TextArea();
        comentariosField.setWidthFull();

        Button confirmarComentariosButton = new Button("Confirmar");
        confirmarComentariosButton.addClickListener(e -> {
            addComentariosAReclamacion(complaint, comentariosField.getValue());
            actualizarTablaReclamacionesActivas();
            comentariosDialog.close();
        });

        comentariosFormLayout.add(comentariosLabel, comentariosField, confirmarComentariosButton);
        comentariosDialog.add(comentariosFormLayout);

        comentariosDialog.open();
    }

    private void mostrarComentarios(Complaint complaint) {
        Dialog comentariosDialog = new Dialog();
        comentariosDialog.setCloseOnOutsideClick(true);

        String comentarios = String.join("\n", complaint.getComments());

        TextArea comentariosTextArea = new TextArea();
        comentariosTextArea.setValue(comentarios);
        comentariosTextArea.setReadOnly(true);
        comentariosTextArea.getStyle().set("width", "800px");

        comentariosDialog.add(comentariosTextArea);

        comentariosDialog.open();
    }

    private void mostrarOpcionesCambioEstado(Complaint complaint) {
        FormLayout formLayout = new FormLayout();
    
        RadioButtonGroup<Complaint.ComplaintStatus> estadoRadioButtonGroup = new RadioButtonGroup<>();
        estadoRadioButtonGroup.setLabel("Seleccionar Estado");
        estadoRadioButtonGroup.setItems(Complaint.ComplaintStatus.values());
        estadoRadioButtonGroup.setValue(Complaint.ComplaintStatus.EN_ESPERA);
    
        Button confirmarEstadoButton = new Button("Cambiar Estado");
        confirmarEstadoButton.addClickListener(e -> {
            Long reclamacionId = complaint.getId();
            complaintService.cambiarEstadoReclamacion(reclamacionId, estadoRadioButtonGroup.getValue());
            actualizarTablaReclamacionesActivas();
            dialog.close();
        });


    
        formLayout.add(estadoRadioButtonGroup, confirmarEstadoButton);
        dialog.add(formLayout);
    }



    private void actualizarTablaReclamacionesActivas() {
        List<Complaint> reclamaciones = complaintService.getComplaints()
                .stream()
                .filter(complaint -> complaint.getStatus() == Complaint.ComplaintStatus.EN_ESPERA || complaint.getStatus() == Complaint.ComplaintStatus.EN_PROCESO)
                .collect(Collectors.toList());

        grid.setItems(reclamaciones);
    }
}