package es.uca.iw.views.editarFacturas;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import java.time.LocalDate;
import es.uca.iw.services.BillService;
import es.uca.iw.model.Bill;
import java.util.*;
import java.math.*;


public class BillGalleryViewCard extends ListItem {
    private BillService billService;
    private final long billId;

    public BillGalleryViewCard(BillService billService, long billId, LocalDate date, int dataConsumed, BigDecimal dataTotalPrice, int minutesConsumed, BigDecimal totalPrice, long contractId) {
        this.billService = billService;
        this.billId = billId;


        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM, BorderRadius.LARGE);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.SMALL, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        div.setHeight("250px");
        div.getStyle().set("width", "100%");



        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText("Factura #" + billId);

        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle.setText("Precio: " + totalPrice);

        Span subtitle2 = new Span();
        subtitle2.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle2.setText("Fecha: " + date);

        Span subtitle3 = new Span();
        subtitle3.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle3.setText("Contrato: " + contractId);

        Span subtitle4 = new Span();
        subtitle4.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle4.setText("Datos consumidos: " + dataConsumed);

        Span subtitle5 = new Span();
        subtitle5.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle5.setText("Precio de los datos: " + dataTotalPrice);

        Span subtitle6 = new Span();
        subtitle6.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle6.setText("Minutos consumidos: " + minutesConsumed);

        Span subtitle7 = new Span();
        subtitle7.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle7.setText("Precio de las llamadas: " + totalPrice);

        Span subtitle8 = new Span();
        subtitle8.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle8.setText("Precio total: " + totalPrice);


        add(header, subtitle, subtitle2, subtitle3, subtitle4, subtitle5, subtitle6, subtitle7, subtitle8);

        // Botones de editar y borrar
        Button editButton = new Button("Editar Contrato", event -> {
            Optional<Bill> optionalBill = this.billService.getBillById(billId);
            if (optionalBill.isPresent()) {
                Bill bill = optionalBill.get();
                Dialog dialog = new Dialog();

                TextField dataConsumedField = new TextField("Datos consumidos");
                TextField minutesConsumedField = new TextField("Minutos consumidos");
                TextField dateField = new TextField("Fecha (yyyy-mm-DD)");
                TextField contractField = new TextField("Contrato ID");
                
                dataConsumedField.setValue(String.valueOf(bill.getdataConsumed()));
                minutesConsumedField.setValue(String.valueOf(bill.getminutesConsumed()));
                dateField.setValue(bill.getDate().toString());
                contractField.setValue(String.valueOf(bill.getContract().getId()));


                Button saveButton = new Button("Guardar", saveEvent -> {
                    
                    String dataConsumedString = dataConsumedField.getValue();
                    String minutesConsumedString = minutesConsumedField.getValue();
                    String dateString = dateField.getValue();
                    String contractString = contractField.getValue();

                    this.billService.editBillWithDetails(this.billId, dataConsumedString, minutesConsumedString, dateString, contractString);
                    dialog.close();
                    UI.getCurrent().getPage().reload();
                    
            });
            Button cancelButton = new Button("Cancelar", cancelEvent -> dialog.close());

            dialog.add(dataConsumedField, minutesConsumedField, dateField, contractField, saveButton, cancelButton);
            dialog.open();
        } else {
            Notification.show("Factura no encontrada");
        }
    });

        Button deleteButton = new Button("Borrar Factura", event -> {
            billService.deleteBill(billId);
            UI.getCurrent().getPage().reload();
        });

        // Estilo de los botones
        editButton.getStyle().set("color", "black");
        deleteButton.getStyle().set("color", "black");

        // Agrega botones a la vista
        add(editButton, deleteButton);
    }
}
