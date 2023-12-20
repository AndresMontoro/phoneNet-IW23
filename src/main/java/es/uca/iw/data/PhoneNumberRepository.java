package es.uca.iw.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.uca.iw.model.PhoneNumber;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
    @Query("SELECT p FROM PhoneNumber p WHERE p.available = true ORDER BY p.id ASC")
    Optional<PhoneNumber> findFirstAvailableNumber();
}
