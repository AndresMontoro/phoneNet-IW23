package es.uca.iw.views.editarProductos;
import java.math.BigDecimal;
import java.util.Optional;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;
import es.uca.iw.services.EditarProductosService;
import es.uca.iw.model.Product;

public class ImageGalleryViewCard2 extends ListItem {
    private EditarProductosService editarProductosService;

    public ImageGalleryViewCard2(EditarProductosService editarProductosService,long productId, String productName, String productUrl, String productDescription, BigDecimal productPrice, boolean hireVisible) {

        this.editarProductosService = editarProductosService;

        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.SMALL, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        div.setHeight("250px");
        div.getStyle().set("width", "100%");

        Image image = new Image();
        image.setWidth("100%");
        image.setSrc(productUrl);
        image.setAlt(productDescription);

        div.add(image);

        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(productName);

        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle.setText(productPrice.toString() + "€/mes");

        Paragraph description = new Paragraph(productDescription);
        description.addClassName(Margin.Vertical.MEDIUM);

        add(div, header, subtitle, description);

//////////////////////////////////////////////////////////////

        if (hireVisible) { // hireVisible no sera true en esta parte, solo para pruebas
                    
///////////////////////////////////////////////////////////////////

        } else {
        // Boton de editar
        Button editButton = new Button("Editar Producto", event -> {
            Optional<Product> optionalProduct = this.editarProductosService.findById(productId);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                Dialog dialog = new Dialog();
        
                TextField newNameField = new TextField("Nuevo Nombre");
                TextField newDescriptionField = new TextField("Nueva Descripción");
                TextField newImageUrlField = new TextField("Nueva URL de Imagen");
                TextField newPriceField = new TextField("Nuevo Precio");

                newNameField.setValue(product.getName());
                newDescriptionField.setValue(product.getDescription());
                newImageUrlField.setValue(product.getImage());
                newPriceField.setValue(product.getPrice().toString());
       
                Button saveButton = new Button("Guardar", saveEvent -> {
                    String newProductName = newNameField.getValue();
                    String newProductDescription = newDescriptionField.getValue();
                    String newProductImageUrl = newImageUrlField.getValue();
                    BigDecimal newProductPrice = new BigDecimal(newPriceField.getValue()); // Obtiene el nuevo precio

        
                    // Actualizar cambios 
                    product.setName(newProductName);
                    product.setDescription(newProductDescription);
                    product.setImage(newProductImageUrl);
                    product.setPrice(newProductPrice);
        
                    // Actualizar cambios en la BD
                    this.editarProductosService.saveProduct(product);
                    dialog.close();
        
                    // Recargar la página
                    UI.getCurrent().getPage().executeJs("location.reload();");
                });
        
                Button cancelButton = new Button("Cancelar", cancelEvent -> dialog.close());
        
                // Agregar componentes
                dialog.add(newNameField, newDescriptionField, newImageUrlField, newPriceField, saveButton, cancelButton);
                dialog.open();
            } else {
                Notification.show("Producto no encontrado");
            }
        });

        ///////////////////////////////////////////////////////////////////////////
        //Boton de borrar
        Button deleteButton = new Button("Borrar Producto", event -> {
            Optional<Product> optionalProduct = this.editarProductosService.findById(productId);
            if (optionalProduct.isPresent()) {
                this.editarProductosService.deleteProduct(optionalProduct.get().getId());
                Notification.show("Producto borrado correctamente");

                // Recarga la página para que se actualice la lista de productos
                UI.getCurrent().getPage().executeJs("location.reload();"); 

            } else {
                Notification.show("Producto no encontrado");
            }
        });

        // Diseño de los botones
        editButton.getStyle().set("color", "black");
        deleteButton.getStyle().set("color", "black");
        add(editButton, deleteButton);
        }
    }
}
