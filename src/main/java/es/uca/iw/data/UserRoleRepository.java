package es.uca.iw.data;

import org.springframework.data.jpa.repository.JpaRepository;

import es.uca.iw.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
    
}
