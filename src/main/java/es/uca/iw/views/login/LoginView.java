package es.uca.iw.views.login;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import es.uca.iw.components.ContactUsComponent;

@Route("login")
@AnonymousAllowed
public class LoginView extends VerticalLayout {

    public LoginView() {
        setHeight("100vh");  // Establece la altura del contenedor principal al 100% de la vista

        getStyle()
                .set("background-color", "var(--lumo-contrast-5pct)")
                .set("display", "flex")
                .set("justify-content", "center")
                .set("padding", "var(--lumo-space-l)")
                .set("align-items", "center");

        LoginForm loginForm = new LoginForm();
        loginForm.getStyle().set("padding-bottom", "0");
        loginForm.setForgotPasswordButtonVisible(false);
        loginForm.setAction("login");

        Button registerButton = new Button("¿Aún no tienes cuenta?");
        registerButton.addClickListener(e -> {
            registerButton.getUI().ifPresent(ui -> ui.navigate("Registro"));
        });

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.add(loginForm);
        formLayout.add(registerButton);
        formLayout.setAlignItems(Alignment.CENTER);
        formLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        formLayout.setSpacing(false);
        formLayout.setPadding(false);
        formLayout.setMargin(false);
        formLayout.getStyle().set("width", "auto");
        formLayout.getStyle().set("background-color", "white");
        formLayout.getStyle().set("padding-bottom", "1rem");
        formLayout.setHeight("100%");

        VerticalLayout container = new VerticalLayout();
        container.add(formLayout);
        container.add(new ContactUsComponent());
        container.setAlignItems(Alignment.STRETCH);  
        container.setHeight("100%");  

        add(container);
    }
}
