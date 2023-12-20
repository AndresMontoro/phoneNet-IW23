package es.uca.iw.views.Reclamaciones;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.PermitAll;

import java.time.LocalDate;

@Route(value = "Reclamaciones", layout = MainLayout.class)
@PermitAll
public class ReclamacionesView extends VerticalLayout {

    public ReclamacionesView() {
        // Simulación de datos de reclamaciones con IDs únicos
        Reclamacion reclamacion1 = new Reclamacion(1, "Problema con el servicio", LocalDate.now(), "Pendiente", "");
        Reclamacion reclamacion2 = new Reclamacion(2, "Facturación incorrecta", LocalDate.now().minusDays(2), "En proceso", "");

        // Crear una tabla para mostrar las reclamaciones
        Grid<Reclamacion> grid = new Grid<>(Reclamacion.class);
        grid.setItems(reclamacion1, reclamacion2);
        grid.setColumns("id", "descripcion", "fechaCreacion", "estado", "comentarios");

        // Personalizar la visualización de la fecha
        grid.getColumnByKey("fechaCreacion").setHeader("Fecha de Creación");

        // Añadir una columna de botón para acciones
        grid.addComponentColumn(reclamacion -> {
            Button accionesButton = new Button("Acciones");
            accionesButton.addClickListener(e -> mostrarAcciones(reclamacion));
            return accionesButton;
        }).setHeader("Acciones");

        // Botón para añadir reclamaciones
        Button añadirReclamacionButton = new Button("Añadir Reclamación");
        añadirReclamacionButton.addClickListener(e -> mostrarFormularioAñadirReclamacion());

        add(new Span("Reclamaciones"));
        add(añadirReclamacionButton);
        add(grid);
    }

    // Método para mostrar las acciones de una reclamación
    private void mostrarAcciones(Reclamacion reclamacion) {
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

    // Método para mostrar el formulario de añadir reclamación
    private void mostrarFormularioAñadirReclamacion() {
        // Implementar lógica para mostrar el formulario de añadir reclamación
    }

    // Clase para representar una reclamación
    public static class Reclamacion {
        private int id;
        private String descripcion;
        private LocalDate fechaCreacion;
        private String estado;
        private String comentarios;

        public Reclamacion(int id, String descripcion, LocalDate fechaCreacion, String estado, String comentarios) {
            this.id = id;
            this.descripcion = descripcion;
            this.fechaCreacion = fechaCreacion;
            this.estado = estado;
            this.comentarios = comentarios;
        }

        public int getId() {
            return id;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public LocalDate getFechaCreacion() {
            return fechaCreacion;
        }

        public String getEstado() {
            return estado;
        }

        public String getComentarios() {
            return comentarios;
        }

        public void setComentarios(String comentarios) {
            this.comentarios = comentarios;
        }
    }
}
