package es.uca.iw.views.editarProductos;

import jakarta.annotation.security.PermitAll;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import es.uca.iw.model.Product;
import es.uca.iw.services.ProductService;
import es.uca.iw.views.MainAdminLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import es.uca.iw.services.EditarProductosService;
import java.util.Set;
import com.vaadin.flow.component.notification.Notification;

@Route(value = "EditarProductos", layout = MainAdminLayout.class)
@PermitAll
public class EditarProductosView extends VerticalLayout {

    private final EditarProductosService editarProductosService;
    private List<Product> products;
    private OrderedList imageContainer;
    private ComboBox<String> nameComboBox;
    private Button clearFilterButton;
    private Button addButton;

    public EditarProductosView(ProductService productService, EditarProductosService editarProductosService) {
        this.editarProductosService = editarProductosService;
        products = productService.findAll();
        creartext();
        loadComboBoxItems();

        for (Product product : products) {
            if (product.getAvailable()) {
                imageContainer.add(new ImageGalleryViewCard2(editarProductosService,product.getId(), product.getName(), product.getImage(),
                        product.getDescription(), product.getPrice(), false));
            }
        }
    }

    private void creartext() {
        addClassNames("image-gallery-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);
        

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Lista de productos disponibles"); // texto 1
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        headerContainer.add(header);

        nameComboBox = new ComboBox<>("Seleccione un Producto");
        nameComboBox.setAllowCustomValue(true);
        nameComboBox.addValueChangeListener(event -> filterProductsByName(event.getValue()));

        clearFilterButton = new Button("Limpiar filtro", event -> {
            nameComboBox.setValue(null);
            showAllProducts();
        });

        addButton = new Button("Añadir Nuevo Producto", event -> {
            Dialog dialog = new Dialog();

            TextField newNameField = new TextField("Nombre");
            TextField newDescriptionField = new TextField("Descripción");
            TextField newImageUrlField = new TextField("URL de Imagen");
            TextField newPriceField = new TextField("Precio");

            Checkbox availableCheckbox = new Checkbox("Disponible");
            Checkbox fiberTypeCheckbox = new Checkbox("Fibra");
            Checkbox mobileTypeCheckbox = new Checkbox("Móvil");
            Checkbox fixedTypeCheckbox = new Checkbox("Fijo");

            //No se puede seleccionar mas de un tipo de producto a la vez
            fiberTypeCheckbox.addValueChangeListener(fiberEvent -> {
                if (fiberEvent.getValue()) {
                    mobileTypeCheckbox.setValue(false);
                    fixedTypeCheckbox.setValue(false);
                }
            });
            
            mobileTypeCheckbox.addValueChangeListener(mobileEvent -> {
                if (mobileEvent.getValue()) {
                    fiberTypeCheckbox.setValue(false);
                    fixedTypeCheckbox.setValue(false);
                }
            });
            
            fixedTypeCheckbox.addValueChangeListener(fixedEvent -> {
                if (fixedEvent.getValue()) {
                    fiberTypeCheckbox.setValue(false);
                    mobileTypeCheckbox.setValue(false);
                }
            });

            Button saveButton = new Button("Guardar", saveEvent -> {
                String newProductName = newNameField.getValue();
                String newProductDescription = newDescriptionField.getValue();
                String newProductImageUrl = newImageUrlField.getValue();
                BigDecimal newProductPrice = new BigDecimal(newPriceField.getValue());

                Product newProduct = new Product();
                newProduct.setName(newProductName);
                newProduct.setDescription(newProductDescription);
                newProduct.setImage(newProductImageUrl);
                newProduct.setPrice(newProductPrice);
                newProduct.setAvailable(availableCheckbox.getValue());
                Set<Product.ProductType> productTypes = new HashSet<>();
                if (fiberTypeCheckbox.getValue()) productTypes.add(Product.ProductType.FIBRA);
                if (mobileTypeCheckbox.getValue()) productTypes.add(Product.ProductType.MOVIL);
                if (fixedTypeCheckbox.getValue()) productTypes.add(Product.ProductType.FIJO);
                newProduct.setProductType(productTypes);

                editarProductosService.saveProduct(newProduct);
                Notification.show("Producto añadido con éxito.");
                dialog.close();
                UI.getCurrent().getPage().reload();
            });

            Button cancelButton = new Button("Cancelar", cancelEvent -> dialog.close());

            dialog.add(newNameField, newDescriptionField, newImageUrlField, newPriceField, availableCheckbox, fiberTypeCheckbox, mobileTypeCheckbox, fixedTypeCheckbox,
                    saveButton, cancelButton);

            dialog.open();
        });

        nameComboBox.getStyle().set("align-self", "center");
        nameComboBox.getStyle().set("margin-bottom", "2em");    
        clearFilterButton.getStyle().set("align-self", "center");
        addButton.getStyle().set("margin-top", "2.25em");
        
        add(headerContainer);
        add(new HorizontalLayout(nameComboBox, clearFilterButton, addButton));

        imageContainer = new OrderedList();
        imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);
        add(imageContainer);
    }

    private void loadComboBoxItems() {
        nameComboBox.setItems(products.stream().map(Product::getName).collect(Collectors.toList()));
    }

    private void filterProductsByName(String selectedName) {
        if (selectedName == null || selectedName.isEmpty()) {
            showAllProducts();
            return;
        }

        List<Product> filteredProducts = products.stream()
                .filter(product -> product.getName().equals(selectedName))
                .collect(Collectors.toList());

        updateImageContainer(filteredProducts);
    }

    private void updateImageContainer(List<Product> filteredProducts) {
        imageContainer.removeAll();

        if (filteredProducts.isEmpty()) {
            showAllProducts();
        } else {
            for (Product product : filteredProducts) {
                if (product.getAvailable()) {
                    imageContainer.add(new ImageGalleryViewCard2(editarProductosService,product.getId(), product.getName(),
                            product.getImage(), product.getDescription(), product.getPrice(), false));
                }
            }
        }
    }

    public void showAllProducts() {
        // Mostrar todos los productos
        imageContainer.removeAll();
        for (Product product : products) {
            if (product.getAvailable())
                imageContainer.add(new ImageGalleryViewCard2(editarProductosService,product.getId(), product.getName(), product.getImage(),
                        product.getDescription(), product.getPrice(), false));
        }
    }

}
