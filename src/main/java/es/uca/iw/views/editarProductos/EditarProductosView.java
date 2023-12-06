package es.uca.iw.views.editarProductos;
import jakarta.annotation.security.PermitAll;
import java.util.List;
import java.util.stream.Collectors;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
import es.uca.iw.views.MainLayout;

import com.vaadin.flow.component.button.Button;
import es.uca.iw.services.EditarProductosService;


@Route(value = "EditarProductos", layout = MainLayout.class)
@PermitAll
public class EditarProductosView extends VerticalLayout {

    private final ProductService productService;
    private final EditarProductosService editarProductosService;
    private List<Product> products;
    private OrderedList imageContainer;
    private ComboBox<String> nameComboBox;
    private Button clearFilterButton;

    public EditarProductosView(ProductService productService, EditarProductosService editarProductosService) {
        this.productService = productService;
        this.editarProductosService = editarProductosService;
        products = productService.findAll();
        creartext();
        loadComboBoxItems();

        for (Product product : products) {
            if (product.getAvailable()) {
                imageContainer.add(new ImageGalleryViewCard2(productService,editarProductosService,product.getId(), product.getName(), product.getImage(),
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

        // Filtro de productos
        nameComboBox = new ComboBox<>("Seleccione un Producto");
        nameComboBox.setAllowCustomValue(true);
        nameComboBox.addValueChangeListener(event -> filterProductsByName(event.getValue()));

        // Botón para limpiar el filtro
        clearFilterButton = new Button("Limpiar filtro", event -> {
            nameComboBox.setValue(null);
            showAllProducts();
        });

        // Configuracion de estilos
        nameComboBox.getStyle().set("align-self", "center");
        nameComboBox.getStyle().set("margin-bottom", "2em"); 
        
        clearFilterButton.getStyle().set("align-self", "center");

        add(headerContainer);
        add(new HorizontalLayout(nameComboBox, clearFilterButton));

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

        // Filtrar la lista de productos según el nombre seleccionado
        List<Product> filteredProducts = products.stream()
                .filter(product -> product.getName().equals(selectedName))
                .collect(Collectors.toList());

        // Limpiar y volver a mostrar los productos filtrados o todos los productos
        updateImageContainer(filteredProducts);
    }



    private void updateImageContainer(List<Product> filteredProducts) {
        imageContainer.removeAll();

        if (filteredProducts.isEmpty()) {
            showAllProducts();
        } else {
            for (Product product : filteredProducts) {
                if (product.getAvailable()) {
                    imageContainer.add(new ImageGalleryViewCard2(productService,editarProductosService,product.getId(), product.getName(),
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
                imageContainer.add(new ImageGalleryViewCard2(productService, editarProductosService,product.getId(), product.getName(), product.getImage(),
                        product.getDescription(), product.getPrice(), false));
        }
    }

}
