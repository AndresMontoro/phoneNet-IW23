package es.uca.iw.services;

// import java.nio.charset.StandardCharsets;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.UUID;
// import java.util.regex.Matcher;
// import java.util.regex.Pattern;

public class PasswordRecoveryService {


    public static String generateRecoveryRequest(String email) {

        String encodedEmail = email.replace("@", "%40").replace(".", "%2E");
        return "cambiar-contrasena?email=" + encodedEmail;
    }

    public static boolean isValidEmailLink(String emailLink) {

        return emailLink.matches("cambiar-contrasena\\?email=.*");
    }
}