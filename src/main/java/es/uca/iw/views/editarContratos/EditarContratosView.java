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
//import es.uca.iw.model.Product;
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
    private Button clearFilterButton;
    private Button addButton;
    private ComboBox<String> dniFilterComboBox;
    private ComboBox<String> tarifaFilterComboBox;

    public EditarContratosView(ContractService contractService) {
        this.contractService = contractService;
        contracts = contractService.getAllContracts();
        creartext();
        loadDniComboBoxItems();
        loadTarifaComboBoxItems();

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

        clearFilterButton = new Button("Limpiar filtro", event -> {
            tarifaFilterComboBox.setValue(null);
            dniFilterComboBox.setValue(null);
            loadTarifaComboBoxItems(); // Vuelve a cargar todas las tarifas

            showAllContracts();
        });

        // Filtro de contratos por DNI
        dniFilterComboBox = new ComboBox<>("Filtrar por DNI");
        dniFilterComboBox.setAllowCustomValue(true);
        dniFilterComboBox.addValueChangeListener(event -> filterContractsByDNI(event.getValue()));

        // Filtro de contratos por nombre de tarifa
        tarifaFilterComboBox = new ComboBox<>("Filtrar por Tarifa");
        tarifaFilterComboBox.setAllowCustomValue(true);
        tarifaFilterComboBox.addValueChangeListener(event -> filterContractsByTarifa(event.getValue()));




    
        addButton = new Button("Añadir Nuevo Contrato", event -> {
            Dialog dialog = new Dialog();
           
            TextField phoneNumber = new TextField("Número de teléfono");
            TextField productName = new TextField("Nombre del producto");
            TextField userDNI = new TextField("DNI del usuario");

            Button saveButton = new Button("Guardar", saveEvent -> {

                String phoneNumberString = phoneNumber.getValue();
                String productNameString = productName.getValue();
                String userDNIString = userDNI.getValue();


                boolean dniExists = contractService.checkIfDNIExists(userDNIString);
                boolean productNameExists = contractService.checkIfProductNameExists(productNameString);
                if (!dniExists && !productNameExists) {
                    Notification.show("Ni el producto existe ni el DNI existe para ningun usuario", 5000, Notification.Position.BOTTOM_START);
                } else if (!dniExists) {
                    Notification.show("El DNI no existe para ningun usuario", 5000, Notification.Position.BOTTOM_START);
                } else if (!productNameExists) {
                    Notification.show("El producto no existe", 5000, Notification.Position.BOTTOM_START);
                } else {
                    try {
                        contractService.saveContractWithDetails(phoneNumberString, productNameString, userDNIString);
                        Notification.show("Contrato añadido con éxito.");
                        UI.getCurrent().getPage().reload();
                        dialog.close();
                    } catch (Exception e) {
                        Notification.show("Error al agregar el contrato: " + e.getMessage(), 5, Notification.Position.BOTTOM_CENTER);
                    }
                }
            });



            Button cancelButton = new Button("Cancelar", cancelEvent -> dialog.close());
       
            dialog.add(phoneNumber, productName, userDNI, saveButton, cancelButton);
            dialog.open();
        });
    
   
        dniFilterComboBox.getStyle().set("align-self", "center");
        dniFilterComboBox.getStyle().set("margin-bottom", "2em");
        tarifaFilterComboBox.getStyle().set("align-self", "center");
        tarifaFilterComboBox.getStyle().set("margin-bottom", "2em");
        clearFilterButton.getStyle().set("align-self", "center");
        addButton.getStyle().set("margin-top", "2.25em");
    
        add(headerContainer);
        add(new HorizontalLayout(dniFilterComboBox, tarifaFilterComboBox, clearFilterButton, addButton));
    
        contractContainer = new OrderedList();
        contractContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);
        add(contractContainer);
    }





    private void loadDniComboBoxItems() {
        dniFilterComboBox.setItems(contracts.stream()
                .map(contract -> contract.getUser().getDni())
                .distinct()
                .collect(Collectors.toList()));
    }
    
    private void loadTarifaComboBoxItems() {
        tarifaFilterComboBox.setItems(contracts.stream().map(contract -> contract.getProduct().getName()).collect(Collectors.toList()));
    }
    
    private void loadProductsByDNI(String selectedDNI) {
        List<String> productNames = contracts.stream()
                .filter(contract -> contract.getUser().getDni().equals(selectedDNI))
                .map(contract -> contract.getProduct().getName())
                .collect(Collectors.toList());
    
        tarifaFilterComboBox.setItems(productNames);
    }






    private void filterContractsByDNI(String selectedDNI) {
        if (selectedDNI == null || selectedDNI.isEmpty()) {
            filterContractsByTarifa(tarifaFilterComboBox.getValue()); 
            return;
        }
    
        List<Contract> filteredContracts = contracts.stream()
                .filter(contract -> contract.getUser().getDni().equals(selectedDNI))
                .collect(Collectors.toList());
        loadProductsByDNI(selectedDNI);
        filterContractsByTarifaAndDNI(tarifaFilterComboBox.getValue(), selectedDNI, filteredContracts);
    }


    
    private void filterContractsByTarifa(String selectedTarifa) {
        if (selectedTarifa == null || selectedTarifa.isEmpty()) {
            showAllContracts(); 
            return;
        }
    
        List<Contract> filteredContracts = contracts.stream()
                .filter(contract -> contract.getProduct().getName().equals(selectedTarifa))
                .collect(Collectors.toList());
    
        filterContractsByTarifaAndDNI(selectedTarifa, dniFilterComboBox.getValue(), filteredContracts);
    }
    
    private void filterContractsByTarifaAndDNI(String selectedTarifa, String selectedDNI, List<Contract> filteredContracts) {
        if (selectedDNI != null && !selectedDNI.isEmpty()) {
            filteredContracts = filteredContracts.stream()
                    .filter(contract -> contract.getUser().getDni().equals(selectedDNI))
                    .collect(Collectors.toList());
        }
    
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
