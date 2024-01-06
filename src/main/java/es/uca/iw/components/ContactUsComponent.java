package es.uca.iw.components;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.html.Span;

public class ContactUsComponent extends Div {

    public ContactUsComponent() {
        // Mensaje "Contáctanos"
        Span contactUsText = new Span("Contáctanos");
        contactUsText.getStyle().set("font-weight", "bold").set("font-size", "1.5rem");

        // Enlaces a las redes sociales con iconos de Vaadin y FontAwesome
        Anchor facebookLink = createSocialMediaLink("https://www.facebook.com/tuempresa", VaadinIcon.FACEBOOK.create());
        Anchor twitterLink = createSocialMediaLink("https://twitter.com/PhoneNet134982", VaadinIcon.TWITTER.create());
        Anchor gmailLink = createSocialMediaLink("mailto:PhoneNetIW@gmail.com", VaadinIcon.ENVELOPE.create());

        // Crea una fila horizontal para los elementos
        HorizontalLayout contactLayout = new HorizontalLayout(contactUsText, facebookLink, twitterLink, gmailLink);
        contactLayout.setSpacing(true);
        contactLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        // Añade la fila de elementos al componente principal
        add(contactLayout);
    }

    private Anchor createSocialMediaLink(String url, Icon icon) {
        Anchor anchor = new Anchor(url, icon);
        anchor.setTarget("_blank"); // Abre el enlace en una nueva pestaña
        return anchor;
    }
}