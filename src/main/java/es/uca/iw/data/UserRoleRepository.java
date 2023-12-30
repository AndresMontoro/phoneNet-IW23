package es.uca.iw.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.uca.iw.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
    Optional<UserRole> findByRole(UserRole.Role role);
}
