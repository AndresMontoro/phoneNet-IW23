import org.junit.jupiter.api.Test;
import es.uca.iw.services.UserDetailsServiceImpl;
import es.uca.iw.data.UserRepository;
import es.uca.iw.model.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.*;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void testFindAllUsers() {

        long userId = 13;
        User user = new User();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userDetailsService.deleteUser(userId);

        Mockito.verify(userRepository, Mockito.times(1)).delete(user);
    }
}
