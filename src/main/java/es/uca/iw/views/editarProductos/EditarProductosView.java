package es.uca.iw.views.editarProductos;
import jakarta.annotation.security.PermitAll;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.notification.Notification;
import es.uca.iw.views.MainAdminLayout;

@Route(value = "EditarProductos", layout = MainAdminLayout.class)
@PermitAll
public class EditarProductosView extends VerticalLayout {

    private final ProductService productService;
    private List<Product> products;
    private OrderedList imageContainer;
    private ComboBox<String> nameComboBox;
    private Button clearFilterButton;
    private Button addButton;

    public EditarProductosView(ProductService productService) {
        this.productService = productService;
        products = productService.findAll();
        creartext();
        loadComboBoxItems();

        for (Product product : products) {
                imageContainer.add(new ImageGalleryViewCard2(productService,product.getId(), product.getName(), product.getImage(),
                        product.getDescription(), product.getPrice(), false));
            
        }
    }

    private void creartext() {
        addClassNames("image-gallery-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);
        

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Lista de productos"); // texto 1
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
            TextField newCallPrice = new TextField("Precio por llamada");
            TextField newDataUsagePrice = new TextField("Precio por dato usado");
            TextField newCallLimit = new TextField("Límite de llamadas");
            TextField newDataUsageLimit = new TextField("Límite de datos");
            TextField newCallPenalty = new TextField("Penalización por llamada");
            TextField newDataPenalty = new TextField("Penalización por datos");

            Checkbox availableCheckbox = new Checkbox("Disponible");
            Checkbox fiberTypeCheckbox = new Checkbox("Fibra");
            Checkbox mobileTypeCheckbox = new Checkbox("Móvil");
            Checkbox fixedTypeCheckbox = new Checkbox("Fijo");


            Button saveButton = new Button("Guardar", saveEvent -> {
                String newProductName = newNameField.getValue();
                String newProductDescription = newDescriptionField.getValue();
                String newProductImageUrl = newImageUrlField.getValue();
                BigDecimal newProductPrice = new BigDecimal(newPriceField.getValue());
                BigDecimal newProductCallPrice = new BigDecimal(newCallPrice.getValue());
                BigDecimal newProductDataUsagePrice = new BigDecimal(newDataUsagePrice.getValue());
                Integer newProductCallLimit = Integer.parseInt(newCallLimit.getValue());
                Integer newProductDataUsageLimit = Integer.parseInt(newDataUsageLimit.getValue());
                BigDecimal newProductCallPenalty = new BigDecimal(newCallPenalty.getValue());
                BigDecimal newProductDataPenalty = new BigDecimal(newDataPenalty.getValue());
                boolean available = availableCheckbox.getValue();
     
                Set<Product.ProductType> productTypes = new HashSet<>();
                if (fiberTypeCheckbox.getValue()) productTypes.add(Product.ProductType.FIBRA);
                if (mobileTypeCheckbox.getValue()) productTypes.add(Product.ProductType.MOVIL);
                if (fixedTypeCheckbox.getValue()) productTypes.add(Product.ProductType.FIJO);
        
                productService.saveProductWithDetails(newProductName, newProductDescription, newProductImageUrl,
                        newProductPrice, newProductCallPrice, newProductDataUsagePrice, newProductCallLimit,
                        newProductDataUsageLimit, newProductCallPenalty, newProductDataPenalty, available, productTypes);

                Notification.show("Producto añadido con éxito.");
                dialog.close();
                UI.getCurrent().getPage().reload();
            });

            Button cancelButton = new Button("Cancelar", cancelEvent -> dialog.close());

            dialog.add(newNameField, newDescriptionField, newImageUrlField, newPriceField,newCallPrice, newDataUsagePrice, newCallLimit, newDataUsageLimit, newCallPenalty, newDataPenalty, availableCheckbox, fiberTypeCheckbox, mobileTypeCheckbox, fixedTypeCheckbox, saveButton, cancelButton);

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
                    imageContainer.add(new ImageGalleryViewCard2(productService,product.getId(), product.getName(),
                            product.getImage(), product.getDescription(), product.getPrice(), false));
                
            }
        }
    }

    public void showAllProducts() {
        // Mostrar todos los productos
        imageContainer.removeAll();
        for (Product product : products) {
                imageContainer.add(new ImageGalleryViewCard2(productService,product.getId(), product.getName(), product.getImage(),
                        product.getDescription(), product.getPrice(), false));
        }
    }

}
