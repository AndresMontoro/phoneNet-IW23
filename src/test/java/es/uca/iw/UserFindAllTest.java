package es.uca.iw;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import es.uca.iw.model.User;
import es.uca.iw.services.UserDetailsServiceImpl;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserFindAllTest {

    @Autowired
    private UserDetailsServiceImpl userService;

    @Test
    public void testFindAllUsers() {
        Iterable<User> allUsers = userService.findAll();

        assertNotNull(allUsers);

        assertTrue(allUsers.iterator().hasNext());
    }
}
