package es.uca.iw.views.miPerfil;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin.Horizontal;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin.Vertical;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;

import es.uca.iw.model.User;
import es.uca.iw.services.UserDetailsServiceImpl;
import es.uca.iw.views.MainUserLayout;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "user/MiPerfil", layout = MainUserLayout.class)
@RolesAllowed({"ADMIN", "USER"})
public class MiPerfilView extends VerticalLayout {
    private UserDetailsServiceImpl userService;

    public MiPerfilView(UserDetailsServiceImpl userService) {
        this.userService = userService;
        addClassNames(Padding.MEDIUM, Display.FLEX, FlexDirection.COLUMN, Gap.LARGE,Horizontal.AUTO, Vertical.AUTO);
    
        H2 header = new H2("MI PERFIL");
        header.addClassNames(Horizontal.AUTO, FontSize.XXLARGE, Background.CONTRAST_5, BorderRadius.MEDIUM, Padding.MEDIUM, Margin.SMALL);
        header.getStyle().set("color", "blue");
        add(header);

        User actualUser = this.userService.getAuthenticatedUser().orElse(null);
        if (actualUser != null) {
            TextField nameField = new TextField("Nombre");
            nameField.setValue(actualUser.getName());
            nameField.setReadOnly(true);

            TextField surnameField = new TextField("Apellido");
            surnameField.setValue(actualUser.getSurname());
            surnameField.setReadOnly(true);

            TextField usernameField = new TextField("Usuario");
            usernameField.setValue(actualUser.getUsername());

            TextField dniField = new TextField("DNI");
            dniField.setValue(actualUser.getDni());
            dniField.setReadOnly(true);

            TextField emailField = new TextField("Email");
            emailField.setValue(actualUser.getEmail());

            TextField phoneNumberField = new TextField("Teléfono");
            phoneNumberField.setValue(actualUser.getPhoneNumber() != null ? actualUser.getPhoneNumber() : "");

            HorizontalLayout buttonLayout = new HorizontalLayout();
            buttonLayout.setAlignItems(Alignment.CENTER);
            buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);

            Button saveButton = new Button("Guardar");
            saveButton.addClickListener(event -> {
                try {
                    userService.updateActualUser(surnameField.getValue(), emailField.getValue(), phoneNumberField.getValue());
                    Notification.show("Usuario actualizado correctamente");
                } catch(Exception e) {
                    Notification.show("Error al actualizar el usuario: " + e.getMessage());
                }
            });

            Button changePasswordButton = new Button("Cambiar contraseña");
            changePasswordButton.addClickListener(event -> {
                Dialog changePasswordDialog = new Dialog();
                changePasswordDialog.setCloseOnEsc(false);
                changePasswordDialog.setCloseOnOutsideClick(false);
                
                PasswordField oldPasswordField = new PasswordField("Contraseña actual");
                oldPasswordField.setRequired(true);
                oldPasswordField.setRequiredIndicatorVisible(true);
                oldPasswordField.setAutofocus(true);
                PasswordField newPasswordField = new PasswordField("Nueva contraseña");
                newPasswordField.setRequired(true);
                newPasswordField.setRequiredIndicatorVisible(true);

                PasswordField newPasswordConfirmationField = new PasswordField("Confirmación de la nueva contraseña");
                newPasswordConfirmationField.setRequired(true);
                newPasswordConfirmationField.setRequiredIndicatorVisible(true);
                
                Button cancelBtn = new Button("Cancel", e -> changePasswordDialog.close());
                Button saveBtn = new Button("Save", e -> {
                    try {
                        userService.changePassword(oldPasswordField.getValue(), newPasswordField.getValue(), newPasswordConfirmationField.getValue());
                        getElement().executeJs("location.reload()");
                        Notification.show("Contraseña cambiada correctamente");
                    } catch (Exception exception) {
                        Notification.show("Error al cambiar la contraseña: " + exception.getMessage());
                    }
                });

                HorizontalLayout dialogButtonLayout = new HorizontalLayout(cancelBtn, saveBtn);
                dialogButtonLayout.setAlignItems(Alignment.CENTER);
                dialogButtonLayout.setJustifyContentMode(JustifyContentMode.CENTER);

                VerticalLayout container = new VerticalLayout(oldPasswordField, newPasswordField, newPasswordConfirmationField, dialogButtonLayout);
                changePasswordDialog.add(container);
                changePasswordDialog.open();
            });

            buttonLayout.add(saveButton, changePasswordButton);

            setAlignItems(Alignment.CENTER);
            setJustifyContentMode(JustifyContentMode.CENTER);

            VerticalLayout container = new VerticalLayout(
                nameField, surnameField, dniField, emailField, phoneNumberField, buttonLayout
            );

            container.getStyle().set("border", "1px solid #ccc");
            container.setAlignItems(Alignment.CENTER);
            container.setJustifyContentMode(JustifyContentMode.CENTER);
            add(container);
        }
    }
}
