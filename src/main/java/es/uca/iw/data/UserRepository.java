package es.uca.iw.data;

import org.springframework.data.jpa.repository.JpaRepository;
import es.uca.iw.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}