package es.uca.iw.views.recuperarContraseña;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import es.uca.iw.components.ContactUsComponent;
import es.uca.iw.services.EmailService;
import es.uca.iw.services.PasswordRecoveryService;

@Route("recuperar-contraseña")
@AnonymousAllowed
public class RecuperarContraseñaView extends VerticalLayout {

    public RecuperarContraseñaView() {
        setHeight("100vh");

        H2 title = new H2("Recuperación de Contraseña");
        title.getStyle()
                .set("text-align", "center")
                .set("margin-bottom", "20px");

        EmailField emailField = new EmailField("Correo Electrónico");
        Button recuperarContrasenaButton = new Button("Recuperar Contraseña");

        recuperarContrasenaButton.addClickListener(event -> {
            String email = emailField.getValue();

            PasswordRecoveryService.generateRecoveryRequest(email);

            String recoveryLink = "http://localhost:8080/web/cambiar-contrasena/" + email;

            EmailService.sendRecoveryEmail(email, recoveryLink);

            Notification.show("Se ha enviado un correo de recuperación a " + email);
        });

        VerticalLayout container = new VerticalLayout();
        container.getStyle()
                .set("background-color", "white")
                .set("border-radius", "8px")
                .set("box-shadow", "0 4px 8px rgba(0, 0, 0, 0.1)")
                .set("padding", "20px")
                .set("width", "350px")
                .set("margin", "auto");

        container.add(title, emailField, recuperarContrasenaButton);

        ContactUsComponent contactUsComponent = new ContactUsComponent();
        contactUsComponent.getStyle()
                .set("border-top", "1px solid var(--lumo-contrast-20pct)")
                .set("margin-top", "20px")
                .set("padding-top", "20px");

        container.add(contactUsComponent);

        add(container);
        setAlignItems(Alignment.CENTER); 
    }
}