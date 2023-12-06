package es.uca.iw.views.editarUsuarios;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
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
import es.uca.iw.services.UserService;
import java.util.Optional;
//import es.uca.iw.model.User;
//import es.uca.iw.services.UserService;
//import java.util.Optional;

public class UserGalleryViewCard extends ListItem {
    private UserService userService;
    private final long userId;

    public UserGalleryViewCard(UserService userService, long userId, String userName, String userSurname,
                               String username, String password, User.Role role, String dni, String email) {
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

        add(header, subtitle, dniSpan, emailSpan);


///////////////////////////////////////////////////////////////////////////

        // Boton de editar
        Button editButton = new Button("Editar Usuario", event -> {
            Optional<User> optionalUser = userService.findByUsername(username);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                Dialog dialog = new Dialog();

                TextField newNameField = new TextField("Nuevo Nombre");
                TextField newSurnameField = new TextField("Nuevo Apellido");
                TextField newUsernameField = new TextField("Nuevo Username");
                TextField newPasswordField = new TextField("Nueva Contraseña");
                TextField newDniField = new TextField("Nuevo DNI");
                TextField newEmailField = new TextField("Nuevo Email");

                newNameField.setValue(user.getName());
                newSurnameField.setValue(user.getSurname());
                newUsernameField.setValue(user.getUsername());
                newPasswordField.setValue(user.getPassword());
                newDniField.setValue(user.getDni());
                newEmailField.setValue(user.getEmail());

                Button saveButton = new Button("Guardar", saveEvent -> {
                    String newUserName = newNameField.getValue();
                    String newUserSurname = newSurnameField.getValue();
                    String newUsername = newUsernameField.getValue();
                    String newPassword = newPasswordField.getValue();
                    String newDni = newDniField.getValue();
                    String newEmail = newEmailField.getValue();

                    // Actualizar cambios
                    user.setName(newUserName);
                    user.setSurname(newUserSurname);
                    user.setUsername(newUsername);
                    user.setPassword(newPassword);
                    user.setDni(newDni);
                    user.setEmail(newEmail);

                    // Actualizar cambios en la BD
                    userService.saveUser(user);
                    dialog.close();

                    // Recargar la página
                    UI.getCurrent().getPage().executeJs("location.reload();");
                });

                Button cancelButton = new Button("Cancelar", cancelEvent -> dialog.close());

                // Agregar componentes
                dialog.add(newNameField, newSurnameField, newUsernameField, newPasswordField, newDniField, newEmailField, saveButton, cancelButton);
                dialog.open();
            } else {
                Notification.show("Usuario no encontrado");
            }
        });


//////////////////////////////////////////////////////////////////////////

        //Botón de borrar
        Button deleteButton = new Button("Borrar Usuario", event -> {
            Optional<User> optionalUser = userService.findByUsername(username);
            if (optionalUser.isPresent()) {
                userService.deleteUser(optionalUser.get().getId());
                Notification.show("Usuario borrado correctamente");

                // Recarga la página para que se actualice la lista de usuarios
                UI.getCurrent().getPage().executeJs("location.reload();"); 
            } else {
                Notification.show("Usuario no encontrado");
            }
        });

        //Diseño de los botones
        editButton.getStyle().set("color", "black");
        deleteButton.getStyle().set("color", "black");
        add(editButton, deleteButton);
    }
}
