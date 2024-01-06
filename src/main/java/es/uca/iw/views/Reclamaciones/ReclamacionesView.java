// package es.uca.iw.views.Reclamaciones;

// import com.vaadin.flow.component.button.Button;
// import com.vaadin.flow.component.dialog.Dialog;
// import com.vaadin.flow.component.formlayout.FormLayout;
// import com.vaadin.flow.component.grid.Grid;
// import com.vaadin.flow.component.html.H2;
// import com.vaadin.flow.component.html.Span;
// import com.vaadin.flow.component.orderedlayout.VerticalLayout;
// import com.vaadin.flow.router.Route;
// import com.vaadin.flow.theme.lumo.LumoUtility.Background;
// import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
// import com.vaadin.flow.theme.lumo.LumoUtility.Display;
// import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
// import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
// import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
// import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
// import com.vaadin.flow.theme.lumo.LumoUtility.Margin.Horizontal;
// import com.vaadin.flow.theme.lumo.LumoUtility.Margin.Vertical;
// import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
// import com.vaadin.flow.component.textfield.TextArea;
// import com.vaadin.flow.component.Text;


// import es.uca.iw.services.ComplaintService;
// import es.uca.iw.model.Complaint;
// import es.uca.iw.views.MainUserLayout;
// import jakarta.annotation.security.PermitAll;

// import java.util.List;

// @Route(value = "user/Reclamaciones", layout = MainUserLayout.class)
// @PermitAll
// public class ReclamacionesView extends VerticalLayout {

//     private Grid<Complaint> grid;
//     private ComplaintService complaintService;
//     private Dialog dialog;

//     public ReclamacionesView(ComplaintService complaintService) {
//         this.complaintService = complaintService;

//         addClassNames(Padding.MEDIUM, Display.FLEX, FlexDirection.COLUMN, Gap.LARGE, Horizontal.AUTO, Vertical.AUTO);

//         H2 header = new H2("MIS RECLAMACIONES");
//         header.addClassNames(Horizontal.AUTO, FontSize.XXLARGE, Background.CONTRAST_5, BorderRadius.MEDIUM, Padding.MEDIUM, Margin.SMALL);
//         header.getStyle().set("color", "blue");
//         add(header);

//         grid = new Grid<>(Complaint.class);
//         grid.setColumns("id", "description", "creationDate", "status", "comments");
//         grid.getColumnByKey("id").setHeader("ID");
//         grid.getColumnByKey("description").setHeader("Descripción");
//         grid.getColumnByKey("creationDate").setHeader("Fecha de Creación");
//         grid.getColumnByKey("status").setHeader("Estado");
//         grid.getColumnByKey("comments").setHeader("Comentarios");

//         grid.addComponentColumn(complaint -> {
//             Button accionesButton = new Button("Acciones");
//             accionesButton.addClickListener(e -> mostrarAcciones(complaint));
//             return accionesButton;
//         }).setHeader("Acciones");

//         Button añadirReclamacionButton = new Button("Añadir Reclamación");
//         añadirReclamacionButton.addClickListener(e -> mostrarFormularioAñadirReclamacion());

//         add(añadirReclamacionButton);
//         add(grid);

//         actualizarTablaReclamacionesUsuarioAutenticado();
//     }

//     private void mostrarAcciones(Complaint complaint) {
//         dialog = new Dialog();
//         dialog.setCloseOnOutsideClick(false);

//         FormLayout formLayout = new FormLayout();

//         Button opcion1Button = new Button("Eliminar Reclamación");
//         opcion1Button.addClickListener(e -> eliminarReclamacion(complaint));

//         Button opcion2Button = new Button("Añadir Comentarios");
//         opcion2Button.addClickListener(e -> mostrarFormularioAñadirComentarios(complaint));

//         Button verComentariosButton = new Button("Ver Comentarios");
//         verComentariosButton.addClickListener(e -> mostrarComentarios(complaint));

//         formLayout.add(opcion1Button, opcion2Button, verComentariosButton);
//         dialog.add(formLayout);

//         dialog.open();
//     }

//     private void eliminarReclamacion(Complaint complaint) {
//         complaintService.eliminarReclamacion(complaint.getId());
//         actualizarTablaReclamacionesUsuarioAutenticado();
//         dialog.close();
//     }

//     private void mostrarFormularioAñadirComentarios(Complaint complaint) {
//         Dialog comentariosDialog = new Dialog();
//         comentariosDialog.setCloseOnOutsideClick(false);

//         FormLayout comentariosFormLayout = new FormLayout();

//         Span comentariosLabel = new Span("Comentarios");
//         TextArea comentariosField = new TextArea();
//         comentariosField.setWidthFull();

//         Button confirmarComentariosButton = new Button("Confirmar");
//         confirmarComentariosButton.addClickListener(e -> {
//             addComentariosAReclamacion(complaint, comentariosField.getValue());
//             actualizarTablaReclamacionesUsuarioAutenticado();
//             comentariosDialog.close();
//         });

//         comentariosFormLayout.add(comentariosLabel, comentariosField, confirmarComentariosButton);
//         comentariosDialog.add(comentariosFormLayout);

//         comentariosDialog.open();
//     }

//     private void addComentariosAReclamacion(Complaint complaint, String comentarios) {
//         complaintService.addComentariosAReclamacion(complaint, comentarios);
//     }

//     private void mostrarFormularioAñadirReclamacion() {
//         Dialog dialog = new Dialog();
//         dialog.setCloseOnOutsideClick(false);
    
//         FormLayout formLayout = new FormLayout();
    
//         Span descripcionLabel = new Span("Descripción");
//         TextArea descripcionField = new TextArea();
//         descripcionField.setWidthFull();
    
//         Span comentariosLabel = new Span("Comentarios");
//         TextArea comentariosField = new TextArea();
//         comentariosField.setWidthFull();
    
//         Button confirmarButton = new Button("Confirmar");
//         confirmarButton.addClickListener(e -> {
//             String descripcion = descripcionField.getValue();
//             if (validarDescripcion(descripcion)) {
//                 formLayout.removeAll();
                
//                 Complaint nuevaReclamacion = new Complaint();
//                 nuevaReclamacion.setDescription(descripcion);
    
//                 List<String> nuevosComentarios = List.of(comentariosField.getValue());
//                 nuevaReclamacion.setComments(nuevosComentarios);
    
//                 complaintService.addComplaint(nuevaReclamacion);
//                 actualizarTablaReclamacionesUsuarioAutenticado();
//                 dialog.close();
//             } else {
//                 formLayout.removeAll();
//                 Text errorText = new Text("Descripción inválida, por favor ingrese una descripción.");
//                 formLayout.add(errorText);
//             }
//         });
    
//         formLayout.add(descripcionLabel, descripcionField, comentariosLabel, comentariosField, confirmarButton);
//         dialog.add(formLayout);
    
//         dialog.open();
//     }
    
//     private boolean validarDescripcion(String descripcion) {
//         return descripcion != null && !descripcion.trim().isEmpty();
//     }

//     private void mostrarComentarios(Complaint complaint) {
//         Dialog comentariosDialog = new Dialog();
//         comentariosDialog.setCloseOnOutsideClick(true);

//         String comentarios = String.join("\n", complaint.getComments());

//         TextArea comentariosTextArea = new TextArea();
//         comentariosTextArea.setValue(comentarios);
//         comentariosTextArea.setReadOnly(true);
//         comentariosTextArea.getStyle().set("width", "800px");

//         comentariosDialog.add(comentariosTextArea);

//         comentariosDialog.open();
//     }

//     private void actualizarTablaReclamacionesUsuarioAutenticado() {
//         List<Complaint> reclamaciones = complaintService.getComplaintsByAuthenticatedUser();
//         grid.setItems(reclamaciones);
//     }
// }