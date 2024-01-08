package es.uca.iw.views.editarContratos;
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
import es.uca.iw.model.Contract;
import es.uca.iw.model.Product;
import es.uca.iw.services.ContractService;
import es.uca.iw.views.MainAdminLayout;
import jakarta.annotation.security.RolesAllowed;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;


@Route(value = "admin/EditarContratos", layout = MainAdminLayout.class)
@RolesAllowed("ADMIN")
public class EditarContratosView extends VerticalLayout {

    private final ContractService contractService;
    private List<Contract> contracts;
    private OrderedList contractContainer;
    private ComboBox<String> nameComboBox;
    private Button clearFilterButton;
    private Button addButton;

    public EditarContratosView(ContractService contractService) {
        this.contractService = contractService;
        contracts = contractService.getAllContracts();
        creartext();
        loadComboBoxItems();

        for (Contract contract : contracts) {
            contractContainer.add(new ContractGalleryViewCard(contractService, contract.getId(), contract.getStartDate(),
                    contract.getEndDate(), contract.getUser().getName(), contract.getProduct().getName(),
                    contract.getPhoneNumber(), contract.getProduct().getImage()));
        }
    }

    private void creartext() {
        addClassNames("contract-gallery-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);
    
        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);
    
        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Lista de contratos");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        headerContainer.add(header);

        nameComboBox = new ComboBox<>("Seleccione un Contrato");
        nameComboBox.setAllowCustomValue(true);
        nameComboBox.addValueChangeListener(event -> filterContractsByProductName(event.getValue()));

        clearFilterButton = new Button("Limpiar filtro", event -> {
            nameComboBox.setValue(null);
            showAllContracts();
        });
    
        addButton = new Button("Añadir Nuevo Contrato", event -> {
            Dialog dialog = new Dialog();
           
            TextField phoneNumber = new TextField("Número de teléfono");
            TextField productName = new TextField("Nombre del producto");
            TextField userName = new TextField("Nombre del usuario");

            Button saveButton = new Button("Guardar", saveEvent -> {

                String phoneNumberString = phoneNumber.getValue();
                String productNameString = productName.getValue();
                String userNameString = userName.getValue();
                

                contractService.saveContractWithDetails(phoneNumberString, productNameString, userNameString);
                
                Notification.show("Contrato añadido con éxito.");
                UI.getCurrent().getPage().reload();
            });

            Button cancelButton = new Button("Cancelar", cancelEvent -> dialog.close());
       
            dialog.add(phoneNumber, productName, userName, saveButton, cancelButton);
            dialog.open();
        });
    
        nameComboBox.getStyle().set("align-self", "center");
        nameComboBox.getStyle().set("margin-bottom", "2em");
        clearFilterButton.getStyle().set("align-self", "center");
        addButton.getStyle().set("margin-top", "2.25em");
    
        add(headerContainer);
        add(new HorizontalLayout(nameComboBox, clearFilterButton, addButton));
    
        contractContainer = new OrderedList();
        contractContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);
        add(contractContainer);
    }





    private void loadComboBoxItems() {
        nameComboBox.setItems(contracts.stream().map(Contract::getProduct).map(Product::getName).collect(Collectors.toList()));
    }
    
    private void filterContractsByProductName(String selectedName) {
        if (selectedName == null || selectedName.isEmpty()) {
            showAllContracts();
            return;
        }
    
        List<Contract> filteredContracts = contracts.stream()
                .filter(contract -> contract.getProduct().getName().equals(selectedName))
                .collect(Collectors.toList());
    
        updateContractContainer(filteredContracts);
    }


    private void updateContractContainer(List<Contract> filteredContracts) {
        contractContainer.removeAll();
    
        if (filteredContracts.isEmpty()) {
            showAllContracts();
        } else {
            for (Contract contract : filteredContracts) {
                contractContainer.add(new ContractGalleryViewCard(contractService, contract.getId(), contract.getStartDate(),
                        contract.getEndDate(), contract.getUser().getName(), contract.getProduct().getName(),
                        contract.getPhoneNumber(), contract.getProduct().getImage()));
            }
        }
    }

    public void showAllContracts(){
        contractContainer.removeAll();
        for (Contract contract : contracts) {
            contractContainer.add(new ContractGalleryViewCard(contractService, contract.getId(), contract.getStartDate(),
                    contract.getEndDate(), contract.getUser().getName(), contract.getProduct().getName(),
                    contract.getPhoneNumber(), contract.getProduct().getImage()));
        }
    }


}
