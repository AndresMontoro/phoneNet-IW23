package es.uca.iw.views.editarContratos;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import es.uca.iw.services.ContractService;
import es.uca.iw.model.Contract;
import java.util.*;

public class ContractGalleryViewCard extends ListItem {
    private ContractService contractService;
    private final long contractId;

    public ContractGalleryViewCard(ContractService contractService, long contractId, Date startDate, Date endDate, String userName, String productName, String phoneNumber, String productImageUrl) {
        this.contractService = contractService;
        this.contractId = contractId;

        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM, BorderRadius.LARGE);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.SMALL, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        div.setHeight("250px");
        div.getStyle().set("width", "100%");

        Image image = new Image();
        image.setWidth("100%");
        image.setSrc(productImageUrl);

        div.add(image);

        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText("Contrato #" + contractId);

        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle.setText("Usuario: " + userName);

        Span subtitle2 = new Span();
        subtitle2.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle2.setText("Producto: " + productName);

        Span subtitle3 = new Span();
        subtitle3.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle3.setText("Teléfono: " + phoneNumber);

        Span subtitle4 = new Span();
        subtitle4.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle4.setText("Fecha de Inicio: " + startDate.toString());

        Span subtitle5 = new Span();
        subtitle5.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle5.setText("Fecha final: " + endDate.toString());

        add(div, header, subtitle, subtitle2, subtitle3, subtitle4, subtitle5);

        // Botones de editar y borrar
        Button editButton = new Button("Editar Contrato", event -> {
            Optional<Contract> optionalContract = this.contractService.findById(contractId);
            if (optionalContract.isPresent()) {
                Contract contract = optionalContract.get();
                Dialog dialog = new Dialog();

                TextField userNameField = new TextField("Usuario");
                TextField productNameField = new TextField("Producto");

                userNameField.setValue(contract.getUser().getName());
                productNameField.setValue(contract.getProduct().getName());

                Button saveButton = new Button("Guardar", saveEvent -> {
                    
                    String userNameValue = userNameField.getValue();
                    String productNameValue = productNameField.getValue();

                    contractService.editContractWithDetails(contractId, productNameValue, userNameValue);

                    dialog.close();
                    UI.getCurrent().getPage().reload();
                });

                Button cancelButton = new Button("Cancelar", cancelEvent -> dialog.close());

                dialog.add(userNameField, productNameField, saveButton, cancelButton);
                dialog.open();
            } else {
                Notification.show("Contrato no encontrado");
            }
        });

        Button deleteButton = new Button("Borrar Contrato", event -> {
            contractService.deleteContract(this.contractId);
            UI.getCurrent().getPage().reload();
        });

        // Estilo de los botones
        editButton.getStyle().set("color", "black");
        deleteButton.getStyle().set("color", "black");

        // Agrega botones a la vista
        add(editButton, deleteButton);
    }
}
