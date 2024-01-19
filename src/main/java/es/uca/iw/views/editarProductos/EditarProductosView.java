package es.uca.iw.views.editarProductos;
import jakarta.annotation.security.RolesAllowed;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin.Horizontal;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin.Vertical;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import es.uca.iw.model.Product;
import es.uca.iw.services.ProductService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.notification.Notification;
import es.uca.iw.views.MainAdminLayout;

@Route(value = "admin/EditarProductos", layout = MainAdminLayout.class)
@RolesAllowed("ADMIN")
public class EditarProductosView extends VerticalLayout {

    private final ProductService productService;
    private List<Product> products;
    private OrderedList imageContainer;
    private ComboBox<String> nameComboBox;
    private Button clearFilterButton;
    private Button addButton;

    public EditarProductosView(ProductService productService) {
        addClassNames(Padding.MEDIUM, Display.FLEX, FlexDirection.COLUMN, Gap.LARGE, Horizontal.AUTO, Vertical.AUTO);

        H2 header = new H2("GESTIÓN DE PRODUCTOS");
        header.addClassNames(Horizontal.AUTO, FontSize.XXLARGE, Background.CONTRAST_5, BorderRadius.MEDIUM, Padding.MEDIUM, Margin.SMALL);
        header.getStyle().set("color", "blue");
        add(header);
        
        this.productService = productService;
        products = productService.findAll();
        // creartext();
        // loadComboBoxItems();

        // for (Product product : products) {
        //         imageContainer.add(new ImageGalleryViewCard2(productService,product.getId(), product.getName(), product.getImage(),
        //                 product.getDescription(), product.getPrice(), product.getCallLimit(), product.getDataUsageLimit(), product.getCallPenaltyPrice(), product.getDataPenaltyPrice(), product.getRouterSpeed(),false));
        // }

        Grid<Product> grid = new Grid<>(Product.class, false);
        grid.setWidthFull();
        grid.setItems(products);
        grid.addColumn(Product::getName).setHeader("Nombre");
        grid.addColumn(Product::getDescription).setHeader("Descripción");
        grid.addColumn(Product::getImage).setHeader("Imagen");
        grid.addColumn(Product::getPrice).setHeader("Precio");
        grid.addColumn(Product::getCallLimit).setHeader("Límite de llamadas");
        grid.addColumn(Product::getDataUsageLimit).setHeader("Límite de datos");
        grid.addColumn(Product::getCallPenaltyPrice).setHeader("Penalización por llamada");
        grid.addColumn(Product::getDataPenaltyPrice).setHeader("Penalización por datos");
        grid.addColumn(Product::getRouterSpeed).setHeader("Velocidad del router");
        grid.addColumn(Product::getAvailable).setHeader("Disponible");
        grid.addComponentColumn(product -> createEditButton(product.getId())).setHeader("Acciones");
        add(grid);
    }

    // private void creartext() {
    //     addClassNames("image-gallery-view");
    //     addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO);

    //     HorizontalLayout container = new HorizontalLayout();
    //     container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);
        

    //     VerticalLayout headerContainer = new VerticalLayout();
    //     H2 header = new H2("Lista de productos"); // texto 1
    //     header.addClassNames(Margin.Bottom.NONE, Margin.Top.NONE, FontSize.XXXLARGE);
    //     headerContainer.add(header);

    //     nameComboBox = new ComboBox<>("Seleccione un Producto");
    //     nameComboBox.setAllowCustomValue(true);
    //     nameComboBox.addValueChangeListener(event -> filterProductsByName(event.getValue()));

    //     clearFilterButton = new Button("Limpiar filtro", event -> {
    //         nameComboBox.setValue(null);
    //         showAllProducts();
    //     });

    //     addButton = new Button("Añadir Nuevo Producto", event -> {
    //         Dialog dialog = new Dialog();

    //         TextField newNameField = new TextField("Nombre");
    //         TextField newDescriptionField = new TextField("Descripción");
    //         TextField newImageUrlField = new TextField("URL de Imagen");
    //         TextField newPriceField = new TextField("Precio");
    //         TextField newCallLimit = new TextField("Límite de llamadas");
    //         TextField newDataUsageLimit = new TextField("Límite de datos");
    //         TextField newCallPenalty = new TextField("Penalización por llamada");
    //         TextField newDataPenalty = new TextField("Penalización por datos");
    //         TextField newRouterSpeed = new TextField("Velocidad del router");

    //         Checkbox availableCheckbox = new Checkbox("Disponible");
    //         Checkbox fiberTypeCheckbox = new Checkbox("Fibra");
    //         Checkbox mobileTypeCheckbox = new Checkbox("Móvil");
    //         Checkbox fixedTypeCheckbox = new Checkbox("Fijo");


    //         Button saveButton = new Button("Guardar", saveEvent -> {
    //             String newProductName = newNameField.getValue();
    //             String newProductDescription = newDescriptionField.getValue();
    //             String newProductImageUrl = newImageUrlField.getValue();
    //             BigDecimal newProductPrice = new BigDecimal(newPriceField.getValue());
                
    //             Integer newProductCallLimit = Integer.parseInt(newCallLimit.getValue());
    //             Integer newProductDataUsageLimit = Integer.parseInt(newDataUsageLimit.getValue());
    //             BigDecimal newProductCallPenalty = new BigDecimal(newCallPenalty.getValue());
    //             BigDecimal newProductDataPenalty = new BigDecimal(newDataPenalty.getValue());
    //             Integer newProductRouterSpeed = Integer.parseInt(newRouterSpeed.getValue());
    //             boolean available = availableCheckbox.getValue();
     
    //             Set<Product.ProductType> productTypes = new HashSet<>();
    //             if (fiberTypeCheckbox.getValue()) productTypes.add(Product.ProductType.FIBRA);
    //             if (mobileTypeCheckbox.getValue()) productTypes.add(Product.ProductType.MOVIL);
    //             if (fixedTypeCheckbox.getValue()) productTypes.add(Product.ProductType.FIJO);
        
    //             productService.saveProductWithDetails(newProductName, newProductDescription, newProductImageUrl,
    //                     newProductPrice, newProductCallLimit,
    //                     newProductDataUsageLimit, newProductCallPenalty, newProductDataPenalty, newProductRouterSpeed, available, productTypes);

    //             Notification.show("Producto añadido con éxito.");
    //             dialog.close();
    //             UI.getCurrent().getPage().reload();
    //         });

    //         Button cancelButton = new Button("Cancelar", cancelEvent -> dialog.close());

    //         dialog.add(newNameField, newDescriptionField, newImageUrlField, newPriceField, newCallLimit, newDataUsageLimit, newCallPenalty, newDataPenalty, newRouterSpeed, availableCheckbox, fiberTypeCheckbox, mobileTypeCheckbox, fixedTypeCheckbox, saveButton, cancelButton);

    //         dialog.open();
    //     });

    //     nameComboBox.getStyle().set("align-self", "center");
    //     nameComboBox.getStyle().set("margin-bottom", "2em");    
    //     clearFilterButton.getStyle().set("align-self", "center");
    //     addButton.getStyle().set("margin-top", "2.25em");
        
    //     add(headerContainer);
    //     add(new HorizontalLayout(nameComboBox, clearFilterButton, addButton));

    //     imageContainer = new OrderedList();
    //     imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);
    //     add(imageContainer);
    // }

    // private void loadComboBoxItems() {
    //     nameComboBox.setItems(products.stream().map(Product::getName).collect(Collectors.toList()));
    // }

    // private void filterProductsByName(String selectedName) {
    //     if (selectedName == null || selectedName.isEmpty()) {
    //         showAllProducts();
    //         return;
    //     }

    //     List<Product> filteredProducts = products.stream()
    //             .filter(product -> product.getName().equals(selectedName))
    //             .collect(Collectors.toList());

    //     updateImageContainer(filteredProducts);
    // }

    // private void updateImageContainer(List<Product> filteredProducts) {
    //     imageContainer.removeAll();

    //     if (filteredProducts.isEmpty()) {
    //         showAllProducts();
    //     } else {
    //         for (Product product : filteredProducts) {
    //                 imageContainer.add(new ImageGalleryViewCard2(productService,product.getId(), product.getName(), product.getImage(),
    //                     product.getDescription(), product.getPrice(), product.getCallLimit(), product.getDataUsageLimit(), product.getCallPenaltyPrice(), product.getDataPenaltyPrice(), product.getRouterSpeed(), false));   
    //         }
    //     }
    // }

    // public void showAllProducts() {
    //     // Mostrar todos los productos
    //     imageContainer.removeAll();
    //     for (Product product : products) {
    //             imageContainer.add(new ImageGalleryViewCard2(productService,product.getId(), product.getName(), product.getImage(),
    //                     product.getDescription(), product.getPrice(), product.getCallLimit(), product.getDataUsageLimit(), product.getCallPenaltyPrice(), product.getDataPenaltyPrice(), product.getRouterSpeed(), false));
    //     }
    // }

    private Button createEditButton(Long productId) {
        Button editButton = new Button(VaadinIcon.EDIT.create(), editEvent -> {
            Optional<Product> optionalProduct = this.productService.findById(productId);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                Dialog dialog = new Dialog();

                FormLayout form = new FormLayout();
        
                TextField newNameField = new TextField("Nuevo Nombre");
                TextField newDescriptionField = new TextField("Nueva Descripción");
                TextField newImageUrlField = new TextField("Nueva URL de Imagen");
                TextField newPriceField = new TextField("Nuevo Precio");
                TextField newCallLimit = new TextField("Nuevo Límite de llamadas");
                TextField newDataLimit = new TextField("Nuevo Límite de datos");
                TextField newCallPenalty = new TextField("Nueva Penalización por llamada");
                TextField newDataPenalty = new TextField("Nueva Penalización por datos");
                TextField newRouterSpeed = new TextField("Nueva Velocidad del router");
                Checkbox newAvailable = new Checkbox("Disponible");
        
                newNameField.setValue(product.getName());
                newDescriptionField.setValue(product.getDescription());
                newImageUrlField.setValue(product.getImage());
                newPriceField.setValue(product.getPrice().toString());
                newCallLimit.setValue(String.valueOf(product.getCallLimit()));
                newDataLimit.setValue(String.valueOf(product.getDataUsageLimit()));
                newCallPenalty.setValue(product.getCallPenaltyPrice().toString());
                newDataPenalty.setValue(product.getDataPenaltyPrice().toString());
                newRouterSpeed.setValue(String.valueOf(product.getRouterSpeed()));
                newAvailable.setValue(product.getAvailable());

                form.add(newNameField, newDescriptionField, newImageUrlField, newPriceField,
                    newCallLimit, newDataLimit, newCallPenalty, newDataPenalty,
                    newRouterSpeed, newAvailable);
        
                Button saveButton = new Button("Guardar", saveEvent -> {
                    String newProductName = newNameField.getValue();
                    String newProductDescription = newDescriptionField.getValue();
                    String newProductImageUrl = newImageUrlField.getValue();
                    BigDecimal newProductPrice = new BigDecimal(newPriceField.getValue());
                    int newProductCallLimit = Integer.parseInt(newCallLimit.getValue());
                    int newProductDataLimit = Integer.parseInt(newDataLimit.getValue());
                    BigDecimal newProductCallPenalty = new BigDecimal(newCallPenalty.getValue());
                    BigDecimal newProductDataPenalty = new BigDecimal(newDataPenalty.getValue());
                    int newProductRouterSpeed = Integer.parseInt(newRouterSpeed.getValue());
                    boolean newProductAvailable = newAvailable.getValue();
        
                    // Actualizar cambios
                    this.productService.editProductWithDetails(productId, newProductName, newProductDescription,
                            newProductImageUrl, newProductPrice,
                            newProductCallLimit, newProductDataLimit, newProductCallPenalty,
                            newProductDataPenalty, newProductRouterSpeed, newProductAvailable);
                    dialog.close();
        
                    // Recargar la página
                    UI.getCurrent().getPage().executeJs("location.reload();");
                });
        
                Button cancelButton = new Button("Cancelar", cancelEvent -> dialog.close());

                HorizontalLayout buttonContainer = new HorizontalLayout();
                buttonContainer.add(saveButton, cancelButton);
                buttonContainer.setAlignItems(Alignment.CENTER);
                buttonContainer.setJustifyContentMode(JustifyContentMode.CENTER);
                buttonContainer.setMargin(isAttached());

        
                // Agregar componentes
                dialog.add(form, buttonContainer);
                dialog.open();
            } else {
                Notification.show("Producto no encontrado");
            }
        });

        return editButton;
    }
}
