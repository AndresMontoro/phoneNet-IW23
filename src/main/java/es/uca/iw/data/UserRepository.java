package es.uca.iw.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import es.uca.iw.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByusername(String username);
}