package es.uca.iw.data;

import es.uca.iw.model.Complaint;
import es.uca.iw.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByUser(User user);
}