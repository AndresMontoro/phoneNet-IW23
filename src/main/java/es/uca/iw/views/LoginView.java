package es.uca.iw.views;

import com.vaadin.flow.component.login.LoginForm;
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
        loginForm.setAction("login");
        
        add(loginForm);
    }
}