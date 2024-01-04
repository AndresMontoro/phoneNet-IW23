package es.uca.iw.views.login;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@AnonymousAllowed
public class LoginView extends Div{
    public LoginView(){
        setHeight("91vh");

        getStyle().set("background-color", "var(--lumo-contrast-5pct)")
                .set("display", "flex").set("justify-content", "center")
                .set("padding", "var(--lumo-space-l)")
                .set("align-items", "center");

        LoginForm loginForm = new LoginForm();
        loginForm.getStyle().set("padding-bottom", "0");
        loginForm.setForgotPasswordButtonVisible(false);
        loginForm.setAction("login");

        Button registerButton = new Button("¿Aún no tienes cuenta?");
        registerButton.addClickListener(e -> {
            registerButton.getUI().ifPresent(ui -> ui.navigate("user/Rwegistro"));
        });
        
        VerticalLayout layout = new VerticalLayout();
        layout.add(loginForm);
        layout.add(registerButton);
        layout.setAlignItems(Alignment.CENTER);
        layout.setJustifyContentMode(JustifyContentMode.CENTER);
        layout.setSpacing(false);
        layout.setPadding(false);
        layout.setMargin(false);
        layout.getStyle().set("width", "auto"); 
        layout.getStyle().set("background-color", "white");
        layout.getStyle().set("padding-bottom", "1rem");
                
        add(layout);
    }
}