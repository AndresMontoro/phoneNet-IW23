package es.uca.iw.views.recuperarContraseña;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.router.PageTitle;

import es.uca.iw.services.PasswordRecoveryService;

@Route("cambiar-contrasena")
@AnonymousAllowed
@PageTitle("Cambiar Contraseña")
public class CambiarContraseñaView extends VerticalLayout implements HasUrlParameter<String> {

    private PasswordField newPasswordField;
    private PasswordField confirmPasswordField;

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        if (PasswordRecoveryService.isValidEmailLink(parameter)) {
            
        } else {
            Notification.show("El enlace de recuperación no es válido.");
            event.rerouteTo("recuperar-contrasena");
        }
    }

    public CambiarContraseñaView() {
        newPasswordField = new PasswordField("Nueva Contraseña");
        confirmPasswordField = new PasswordField("Confirmar Contraseña");

        Button cambiarContraseñaButton = new Button("Cambiar Contraseña");

        add(newPasswordField, confirmPasswordField, cambiarContraseñaButton);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }
}