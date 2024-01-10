package es.uca.iw.views.editarFacturas;
import jakarta.annotation.security.RolesAllowed;

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
import es.uca.iw.model.Bill;
import es.uca.iw.services.BillService;
import es.uca.iw.views.MainAdminLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;


@Route(value = "admin/EditarFacturas", layout = MainAdminLayout.class)
@RolesAllowed("ADMIN")
public class EditarFacturasView extends VerticalLayout {

    private final BillService billService;
    private List<Bill> bills;
    private OrderedList billContainer;
    private ComboBox<String> nameComboBox;
    private Button clearFilterButton;
    private Button addButton;

    

    public EditarFacturasView(BillService billService) {
        this.billService = billService;
        bills = billService.getAllBills();
        creartext();
        loadComboBoxItems();

        for (Bill bill : bills) {
            billContainer.add(new BillGalleryViewCard(billService, bill.getId(), bill.getDate(),bill.getdataConsumed(), bill.getDataTotalPrice(), bill.getminutesConsumed(), bill.getTotalPrice(), bill.getContract().getId()));
        }
    }

    private void creartext() {
        addClassNames("bill-gallery-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);
    
        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);
    
        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Lista de facturas");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        headerContainer.add(header);

        nameComboBox = new ComboBox<>("Seleccione una factura");
        nameComboBox.setAllowCustomValue(true);
        nameComboBox.addValueChangeListener(event -> filterBillsByContractId(event.getValue()));

        clearFilterButton = new Button("Limpiar filtro", event -> {
            nameComboBox.setValue(null);
            showAllContracts();
        });
    
        addButton = new Button("Añadir Nueva factura", event -> {
            Dialog dialog = new Dialog();
           
            TextField dataConsumed = new TextField("Datos consumidos");
            TextField minutesConsumed = new TextField("Minutos consumidos");
            TextField date = new TextField("Fecha (yyyy-mm-DD)");
            TextField contract = new TextField("Contrato ID");


            Button saveButton = new Button("Guardar", saveEvent -> {

                String dataConsumedString = dataConsumed.getValue();
                String minutesConsumedString = minutesConsumed.getValue();
                String dateString = date.getValue();
                String contractString = contract.getValue();
                

                billService.saveBillWithDetails(dataConsumedString, minutesConsumedString, dateString, contractString);
                
                Notification.show("Factura añadida con éxito.");
                UI.getCurrent().getPage().reload();
            });

            Button cancelButton = new Button("Cancelar", cancelEvent -> dialog.close());
       
            dialog.add(dataConsumed, minutesConsumed, date, contract, saveButton, cancelButton);
            dialog.open();
        });
    
        nameComboBox.getStyle().set("align-self", "center");
        nameComboBox.getStyle().set("margin-bottom", "2em");
        clearFilterButton.getStyle().set("align-self", "center");
        addButton.getStyle().set("margin-top", "2.25em");
    
        add(headerContainer);
        add(new HorizontalLayout(nameComboBox, clearFilterButton, addButton));
    
        billContainer = new OrderedList();
        billContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);
        add(billContainer);
    }





    private void loadComboBoxItems() {
        nameComboBox.setItems(bills.stream()
                .map(bill -> bill.getContract().getUser().getName())
                .collect(Collectors.toList()));
    }
    
    private void filterBillsByContractId(String selectedName) {
        if (selectedName == null || selectedName.isEmpty()) {
            showAllContracts();
            return;
        }
    
        List<Bill> filteredBills = bills.stream()
                .filter(bill -> bill.getContract().getUser().getName().equals(selectedName))
                .collect(Collectors.toList());
    
        updateBillContainer(filteredBills);
    }


    private void updateBillContainer(List<Bill> filteredBills) {
        billContainer.removeAll();
    
        if (filteredBills.isEmpty()) {
            showAllContracts();
        } else {
            for (Bill bill : filteredBills) {
                billContainer.add(new BillGalleryViewCard(billService, bill.getId(), bill.getDate(),bill.getdataConsumed(), bill.getDataTotalPrice(), bill.getminutesConsumed(), bill.getTotalPrice(), bill.getContract().getId()));
            }
        }
    }

    public void showAllContracts(){
        billContainer.removeAll();
        for (Bill bill : bills) {
            billContainer.add(new BillGalleryViewCard(billService, bill.getId(), bill.getDate(),bill.getdataConsumed(), bill.getDataTotalPrice(), bill.getminutesConsumed(), bill.getTotalPrice(), bill.getContract().getId()));
        }
    }


}
