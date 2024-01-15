package es.uca.iw.views.editarUsuarios;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import es.uca.iw.model.User;
import es.uca.iw.model.UserRole;
import es.uca.iw.services.UserDetailsServiceImpl;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserGalleryViewCard extends ListItem {
    private UserDetailsServiceImpl userService;
    private final long userId;

    public UserGalleryViewCard(UserDetailsServiceImpl userService, long userId, String userName, String userSurname,
                               String username, String password, Set<UserRole> roles, String dni, String email, String phoneNumber) {
        this.userService = userService;
        this.userId = userId;

        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.SMALL, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        div.setHeight("250px");
        div.getStyle().set("width", "100%");

        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(userName + " " + userSurname);

        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle.setText("Username: " + username);

        Span dniSpan = new Span();
        dniSpan.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        dniSpan.setText("DNI: " + dni);

        Span emailSpan = new Span();
        emailSpan.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        emailSpan.setText("Email: " + email);

        Span phoneNumberSpan = new Span();
        phoneNumberSpan.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        phoneNumberSpan.setText("Teléfono: " + phoneNumber);

        add(header, subtitle, dniSpan, emailSpan, phoneNumberSpan);

        Button editButton = new Button("Editar Usuario", event -> {
            Optional<User> optionalUser = this.userService.findByUsername(username);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                Dialog dialog = new Dialog();

                Checkbox adminCheckbox = new Checkbox("Admin");
                Checkbox userCheckbox = new Checkbox("User");
                
                adminCheckbox.setValue(roles.stream().anyMatch(role -> role.getRole() == UserRole.Role.ADMIN));
                userCheckbox.setValue(roles.stream().anyMatch(role -> role.getRole() == UserRole.Role.USER));
                
                TextField newNameField = new TextField("Nuevo Nombre");
                TextField newSurnameField = new TextField("Nuevo Apellido");
                TextField newUsernameField = new TextField("Nuevo Username");
                PasswordField newPasswordField = new PasswordField("Nueva Contraseña");

                TextField newDniField = new TextField("Nuevo DNI");
                TextField newEmailField = new TextField("Nuevo Email");
                TextField newPhoneNumberField = new TextField("Nuevo Número de teléfono");

                newNameField.setValue(user.getName());
                newSurnameField.setValue(user.getSurname());
                newUsernameField.setValue(user.getUsername());
                //newPasswordField.setValue(user.getPassword());
                newDniField.setValue(user.getDni());
                newEmailField.setValue(user.getEmail());
                newPhoneNumberField.setValue(user.getPhoneNumber());

                Button saveButton = new Button("Guardar", saveEvent -> {
                    String newUserName = newNameField.getValue();
                    String newUserSurname = newSurnameField.getValue();
                    String newUsername = newUsernameField.getValue();
                    String newPassword = null;
                    if (!newPasswordField.isEmpty()) {
                        newPassword = newPasswordField.getValue();
                    }
                    //String newPassword = newPasswordField.getValue();
                    String newDni = newDniField.getValue();
                    String newEmail = newEmailField.getValue();
                    String newPhoneNumber = newPhoneNumberField.getValue();

                    Set<UserRole.Role> newRoles = new HashSet<>();
                    if (adminCheckbox.getValue())
                        newRoles.add(UserRole.Role.ADMIN);
                    if (userCheckbox.getValue())
                        newRoles.add(UserRole.Role.USER);

// el primer argumento se corresponde con el id del usuario
                    if (newPassword != null) {
                        userService.editUserWithDetails(user.getId(), newUserName, newUserSurname, newUsername, newPassword, newDni, newEmail, newPhoneNumber, newRoles);
                    } else {
                        userService.editUserWithoutPassword(user.getId(), newUserName, newUserSurname, newUsername, newDni, newEmail, newPhoneNumber, newRoles);
                    }
                    dialog.close();
                    UI.getCurrent().getPage().executeJs("location.reload();");
                });

                Button cancelButton = new Button("Cancelar", cancelEvent -> dialog.close());
                dialog.add(newNameField, newSurnameField, newUsernameField, newPasswordField, newDniField,newEmailField, newPhoneNumberField, adminCheckbox, userCheckbox, saveButton, cancelButton);
                dialog.open();
            } else {
                Notification.show("Usuario no encontrado");
            }
        });

        Button deleteButton = new Button("Borrar Usuario", event -> {
            userService.deleteUser(this.userId);
            UI.getCurrent().getPage().executeJs("location.reload();");
        });

        editButton.getStyle().set("color", "black");
        deleteButton.getStyle().set("color", "black");
        add(editButton, deleteButton);
    }

    
}
