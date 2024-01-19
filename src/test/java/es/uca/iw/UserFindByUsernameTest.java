package es.uca.iw;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import es.uca.iw.model.User;
import es.uca.iw.services.UserDetailsServiceImpl;
import java.util.Optional;

@SpringBootTest
public class UserFindByUsernameTest {

    @Autowired
    private UserDetailsServiceImpl userService;

    @Test
    public void testFindByUsername() {
        String usernameToFind = "Admin";
        Optional<User> foundUserOptional = userService.findByUsername(usernameToFind);

        if (foundUserOptional.isPresent()) {
            User foundUser = foundUserOptional.get();
            assertNotNull(foundUser);
            assertEquals(usernameToFind, foundUser.getUsername());
        } else {
            throw new AssertionError("No se encontró ningún usuario con el nombre de usuario: " + usernameToFind);
        }
    }
}
