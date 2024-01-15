package es.uca.iw.views.editarFacturas;
import jakarta.annotation.security.RolesAllowed;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private Button clearFilterButton;
    private Button addButton;
    private ComboBox<String> dniFilterComboBox;
    private ComboBox<String> fechaFilterComboBox;

    

    public EditarFacturasView(BillService billService) {
        this.billService = billService;
        bills = billService.getAllBills();
        creartext();
        loadDniComboBoxItems();
        loadFechaComboBoxItems();

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

        dniFilterComboBox = new ComboBox<>("Filtrar por DNI");
        dniFilterComboBox.setAllowCustomValue(true);
        dniFilterComboBox.addValueChangeListener(event -> filterBillsByDNI(event.getValue()));

        fechaFilterComboBox = new ComboBox<>("Filtrar por Fecha (yyyy-mm-DD)");
        fechaFilterComboBox.setAllowCustomValue(true);
        fechaFilterComboBox.addValueChangeListener(event -> filterBillsByFecha(event.getValue()));

        clearFilterButton = new Button("Limpiar filtro", event -> {
            fechaFilterComboBox.setValue(null);
            dniFilterComboBox.setValue(null);
            loadFechaComboBoxItems(); 
            showAllBills();
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
    
        fechaFilterComboBox.getStyle().set("align-self", "center");
        fechaFilterComboBox.getStyle().set("margin-bottom", "2em");
        dniFilterComboBox.getStyle().set("align-self", "center");
        dniFilterComboBox.getStyle().set("margin-bottom", "2em");
        clearFilterButton.getStyle().set("align-self", "center");
        addButton.getStyle().set("margin-top", "2.25em");
    
        add(headerContainer);
        add(new HorizontalLayout(dniFilterComboBox, fechaFilterComboBox, clearFilterButton, addButton));
    
        billContainer = new OrderedList();
        billContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);
        add(billContainer);
    }






    private void loadDniComboBoxItems() {
        dniFilterComboBox.setItems(bills.stream()
                .map(bill -> bill.getContract().getUser().getDni())
                .distinct()
                .collect(Collectors.toList()));

    }

    private void loadFechaComboBoxItems() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        fechaFilterComboBox.setItems(bills.stream()
                .map(bill -> bill.getDate().format(formatter))
                .distinct()
                .collect(Collectors.toList()));
    }


    private void loadFechasByDNI(String selectedDNI) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<String> fechasAsociadas = bills.stream()
                .filter(bill -> bill.getContract().getUser().getDni().equals(selectedDNI))
                .map(bill -> bill.getDate().format(dateFormatter))
                .collect(Collectors.toList());
    
        fechaFilterComboBox.setItems(fechasAsociadas);
    }




  
    private void filterBillsByDNI(String selectedDNI) {
        if (selectedDNI == null || selectedDNI.isEmpty()) {
            filterBillsByFecha(fechaFilterComboBox.getValue());
            return;
        }

        List<Bill> filteredBills = bills.stream()
                .filter(bill -> bill.getContract().getUser().getDni().equals(selectedDNI))
                .collect(Collectors.toList());

        loadFechasByDNI(selectedDNI);
        filterBillsByDNIAndFecha(selectedDNI, fechaFilterComboBox.getValue(), filteredBills);
    }


    private void filterBillsByFecha(String selectedFecha) {
        if (selectedFecha == null || selectedFecha.isEmpty()) {
            showAllBills();
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate selectedFechaDate = LocalDate.parse(selectedFecha, formatter);
        List<Bill> filteredBills = bills.stream()
                .filter(bill -> bill.getDate().equals(selectedFechaDate))
                .collect(Collectors.toList());

        filterBillsByDNIAndFecha(dniFilterComboBox.getValue(), selectedFecha, filteredBills);
    }



    private void filterBillsByDNIAndFecha(String selectedDNI, String selectedFecha, List<Bill> filteredBills) {
        if (selectedDNI != null && !selectedDNI.isEmpty()) {
            filteredBills = filteredBills.stream()
                    .filter(bill -> bill.getContract().getUser().getDni().equals(selectedDNI))
                    .collect(Collectors.toList());
        }

        updateBillContainer(filteredBills);
    }


    



    private void updateBillContainer(List<Bill> filteredBills) {
        billContainer.removeAll();
    
        if (filteredBills.isEmpty()) {
            showAllBills();
        } else {
            for (Bill bill : filteredBills) {
                billContainer.add(new BillGalleryViewCard(billService, bill.getId(), bill.getDate(),bill.getdataConsumed(), bill.getDataTotalPrice(), bill.getminutesConsumed(), bill.getTotalPrice(), bill.getContract().getId()));
            }
        }
    }

    public void showAllBills(){
        billContainer.removeAll();
        for (Bill bill : bills) {
            billContainer.add(new BillGalleryViewCard(billService, bill.getId(), bill.getDate(),bill.getdataConsumed(), bill.getDataTotalPrice(), bill.getminutesConsumed(), bill.getTotalPrice(), bill.getContract().getId()));
        }
    }


}
