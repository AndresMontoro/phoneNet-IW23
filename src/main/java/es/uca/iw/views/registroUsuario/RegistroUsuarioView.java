package es.uca.iw.views.registroUsuario;

import java.util.HashSet;
import java.util.Set;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin.Horizontal;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin.Vertical;

import es.uca.iw.services.UserDetailsServiceImpl;
import es.uca.iw.model.UserRole;
import es.uca.iw.data.UserRoleRepository;

import com.vaadin.flow.theme.lumo.LumoUtility.Padding;


@Route("user/Registro")
@AnonymousAllowed
public class RegistroUsuarioView extends Div {

    private UserDetailsServiceImpl userService;
    private UserRoleRepository userRoleRepository;

    public RegistroUsuarioView(UserDetailsServiceImpl userService, UserRoleRepository userRoleRepository) {
        this.userService = userService;
        this.userRoleRepository = userRoleRepository;

        setMinHeight("100vh");

        addClassNames(Padding.MEDIUM, Display.FLEX, FlexDirection.COLUMN, Gap.LARGE,Horizontal.AUTO, Vertical.AUTO);
    
        H2 header = new H2("CREE SU CUENTA");
        header.addClassNames(Horizontal.AUTO, FontSize.XXLARGE, Background.CONTRAST_5, BorderRadius.MEDIUM, Padding.MEDIUM, Margin.SMALL);
        header.getStyle().set("color", "blue");
        add(header);

        FormLayout form = new FormLayout();
        form.addClassNames(Background.CONTRAST_5, BorderRadius.MEDIUM, Padding.MEDIUM, Margin.SMALL);

        VerticalLayout layout = new VerticalLayout();

        TextField nameField = new TextField("Nombre");
        nameField.setRequired(true);
        nameField.setRequiredIndicatorVisible(true);
        nameField.setErrorMessage("Por favor, introduzca su nombre");
        form.add(nameField);

        TextField surnameField = new TextField("Apellido");
        surnameField.setRequired(true);
        surnameField.setRequiredIndicatorVisible(true);
        surnameField.setErrorMessage("Por favor, introduzca su apellido");
        form.add(surnameField);

        // NO FUNCIONA BIEN LA VALIDACION
        TextField usernameField = new TextField("Usuario");
        usernameField.setRequired(true);
        usernameField.setRequiredIndicatorVisible(true);
        usernameField.setErrorMessage("Por favor, introduzca su nombre de usuario");
        usernameField.setValueChangeMode(ValueChangeMode.ON_BLUR);
        usernameField.addBlurListener(evcl -> {
            String enteredUsername = usernameField.getValue();
            
            if (this.userService.existsByUsername(enteredUsername)) {
                usernameField.setInvalid(true);
                usernameField.setErrorMessage("El usuario ya existe");
            } else {
                usernameField.setInvalid(false);
                usernameField.setErrorMessage(null);
            }
        });
        form.add(usernameField);

        PasswordField passwordField = new PasswordField("Contraseña");
        passwordField.setRequired(true);
        passwordField.setRequiredIndicatorVisible(true);
        passwordField.setErrorMessage("Por favor, introduzca su contraseña");
        form.add(passwordField);

        TextField dniField = new TextField("DNI");
        dniField.setRequired(true);
        dniField.setRequiredIndicatorVisible(true);
        dniField.setErrorMessage("Por favor, introduzca su DNI");
        form.add(dniField);

        EmailField emailField = new EmailField("Email");
        emailField.setRequired(true);
        emailField.setRequiredIndicatorVisible(true);
        emailField.setErrorMessage("Por favor, introduzca su email");
        form.add(emailField);

        TextField phoneField = new TextField("Teléfono");
        phoneField.setRequired(true);
        phoneField.setRequiredIndicatorVisible(true);
        phoneField.setErrorMessage("Por favor, introduzca su teléfono");
        form.add(phoneField);

        layout.add(form);

        Button registerButton = new Button("Registrarse");
        registerButton.addClickListener(event -> {
            Set<UserRole> userRoles = new HashSet<>();
            UserRole userRole = this.userRoleRepository.findByRole(UserRole.Role.USER).orElse(null);
            userRoles.add(userRole);
            try {
                this.userService.saveUserWithDetails(nameField.getValue(), surnameField.getValue(), usernameField.getValue(), 
                    passwordField.getValue(), dniField.getValue(), emailField.getValue(), phoneField.getValue(), userRoles);
                Notification.show("Usuario registrado correctamente");
            } catch (Exception e) {
                Notification.show("Error al registrar usuario " + e.getMessage());
                return;
            }
            
        });
        layout.add(registerButton);

        layout.getStyle().set("border", "1px solid #ccc");
        layout.setAlignItems(Alignment.CENTER);
        layout.setJustifyContentMode(JustifyContentMode.CENTER);

        add(layout);
    }
}