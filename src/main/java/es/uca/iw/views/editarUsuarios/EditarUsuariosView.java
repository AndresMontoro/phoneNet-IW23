package es.uca.iw.views.editarUsuarios;

import jakarta.annotation.security.RolesAllowed;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import es.uca.iw.model.User;
import es.uca.iw.model.UserRole;
import es.uca.iw.services.EmailService;
import es.uca.iw.services.UserDetailsServiceImpl;
import com.vaadin.flow.component.notification.Notification;
import java.util.Set;
import com.vaadin.flow.component.checkbox.Checkbox;

import es.uca.iw.views.MainAdminLayout;

@Route(value = "admin/EditarUsuarios", layout = MainAdminLayout.class)
@RolesAllowed("ADMIN")
public class EditarUsuariosView extends VerticalLayout {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private List<User> users;
    private OrderedList userContainer;
    private ComboBox<String> nameComboBox;
    private Button clearFilterButton;
    private Button addButtonUser;


    public EditarUsuariosView(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        users = userDetailsServiceImpl.findAll();
        creartext();
        loadComboBoxItems();

        for (User user : users) {
            userContainer.add(new UserGalleryViewCard(userDetailsServiceImpl, user.getId(), user.getName(), user.getSurname(), user.getUsername(), 
            user.getPassword(),user.getRoles(), user.getDni(), user.getEmail(), user.getPhoneNumber()));
        }
    }

    private void creartext() {
        addClassNames("user-gallery-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Lista de usuarios del sistema"); // texto 1
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        headerContainer.add(header);

        Paragraph subheader = new Paragraph("Recuerde que los campos contraseña y username deben tener más de 5 dígitos");
        subheader.addClassNames(FontSize.MEDIUM, TextColor.SECONDARY);
        headerContainer.add(subheader);

        // Filtro de usuarios
        nameComboBox = new ComboBox<>("Seleccione un DNI del usuario");
        nameComboBox.setAllowCustomValue(true);
        nameComboBox.addValueChangeListener(event -> filterUsersByDNI(event.getValue()));

        // Botón para limpiar el filtro
        clearFilterButton = new Button("Limpiar filtro", event -> {
            nameComboBox.setValue(null);
            showAllUsers();
        });

        addButtonUser = new Button("Añadir Nuevo Usuario", event -> {
            Dialog dialog = new Dialog();

            TextField newNameField = new TextField("Nombre");
            TextField newSurnameField = new TextField("Apellidos");
            TextField newUsernameField = new TextField("Nombre de Usuario");
            TextField newPasswordField = new TextField("Contraseña");
            Checkbox adminCheckbox = new Checkbox("Admin");
            Checkbox userCheckbox = new Checkbox("User");
            TextField newDniField = new TextField("DNI");
            TextField newEmailField = new TextField("Email");
            TextField newPhoneNumberField = new TextField("Número de teléfono");

            Button saveButton = new Button("Guardar", saveEvent -> {
                String newName = newNameField.getValue();
                String newSurname = newSurnameField.getValue();
                String newUsername = newUsernameField.getValue();
                String newPassword = newPasswordField.getValue();
                String newDni = newDniField.getValue();
                String newEmail = newEmailField.getValue();
                String newPhoneNumber = newPhoneNumberField.getValue();
                // Añadir roles al usuario
                Set<UserRole.Role> selectedRoles = new HashSet<>();
                if (adminCheckbox.getValue())
                    selectedRoles.add(UserRole.Role.ADMIN);
                if (userCheckbox.getValue())
                    selectedRoles.add(UserRole.Role.USER);


                Set<UserRole> userRoles = selectedRoles.stream().map(role -> new UserRole(role)).collect(Collectors.toSet());
                try {
                    userDetailsServiceImpl.saveUserWithDetails(newName, newSurname, newUsername, newPassword, 
                        newDni, newEmail, newPhoneNumber, userRoles);

                    String welcomeMessage = "¡Bienvenido a PhoneNet! Gracias por registrarte.";
                    EmailService.sendWelcomeEmail(newEmail, welcomeMessage);
                        
                    Notification.show("Usuario añadido con éxito.");
                    dialog.close();
                    UI.getCurrent().getPage().reload();
                } catch (Exception e) {
                    Notification.show("Error al añadir usuario");
                    return;
                }                
            });

            Button cancelButton = new Button("Cancelar", cancelEvent -> dialog.close());

            dialog.add(adminCheckbox, userCheckbox, newNameField, newSurnameField, newUsernameField, newPasswordField, newDniField, newEmailField, newPhoneNumberField,
                    saveButton, cancelButton);

            dialog.open();
        });


        // Configuración de estilos
        nameComboBox.getStyle().set("align-self", "center");
        nameComboBox.getStyle().set("margin-bottom", "2em");
        clearFilterButton.getStyle().set("align-self", "center");
        addButtonUser.getStyle().set("margin-top", "2.25em");

        add(headerContainer);
        add(new HorizontalLayout(nameComboBox, clearFilterButton, addButtonUser));

        userContainer = new OrderedList();
        userContainer.addClassNames(LumoUtility.Gap.MEDIUM, LumoUtility.Display.GRID, LumoUtility.ListStyleType.NONE,
                LumoUtility.Margin.NONE, LumoUtility.Padding.NONE);
        add(userContainer);
    }




    private void loadComboBoxItems() {
        nameComboBox.setItems(users.stream().map(User::getDni).collect(Collectors.toList()));
    }

    private void filterUsersByDNI(String selectedDNI) {
        if (selectedDNI == null || selectedDNI.isEmpty()) {
            showAllUsers();
            return;
        }

        List<User> filteredUsers = users.stream().filter(user -> user.getDni().equals(selectedDNI))
                .collect(Collectors.toList());

        updateUsersContainer(filteredUsers);
    }

    private void updateUsersContainer(List<User> filteredUsers) {
        userContainer.removeAll();

        if (filteredUsers.isEmpty()) {
            showAllUsers();
        } else {
            for (User user : filteredUsers) {
                if (user.getDni() != null) {
                    userContainer.add(new UserGalleryViewCard(userDetailsServiceImpl, user.getId(), user.getName(),
                            user.getSurname(), user.getUsername(), user.getPassword(),user.getRoles(), user.getDni(),
                            user.getEmail(), user.getPhoneNumber()));
                }
            }
        }
    }

    public void showAllUsers() {
        userContainer.removeAll();
        for (User user : users) {
            if (user.getDni() != null)
                userContainer.add(new UserGalleryViewCard(userDetailsServiceImpl, user.getId(), user.getName(), user.getSurname(), user.getUsername(), 
                user.getPassword(),user.getRoles(), user.getDni(), user.getEmail(), user.getPhoneNumber()));
        }
    }
}
