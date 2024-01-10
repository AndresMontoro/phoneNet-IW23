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

        Span contactUsText = new Span("Contáctanos");
        contactUsText.getStyle().set("font-weight", "bold").set("font-size", "1.5rem");

        Anchor twitterLink = createSocialMediaLink("https://twitter.com/PhoneNet134982", VaadinIcon.TWITTER.create());
        Anchor gmailLink = createSocialMediaLink("mailto:PhoneNetIW@gmail.com", VaadinIcon.ENVELOPE.create());

        HorizontalLayout contactLayout = new HorizontalLayout(contactUsText, twitterLink, gmailLink);
        contactLayout.setSpacing(true);
        contactLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        add(contactLayout);
    }

    private Anchor createSocialMediaLink(String url, Icon icon) {
        Anchor anchor = new Anchor(url, icon);
        anchor.setTarget("_blank"); 
        return anchor;
    }
}