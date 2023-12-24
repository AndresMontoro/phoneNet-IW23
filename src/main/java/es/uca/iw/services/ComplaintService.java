package es.uca.iw.services;

import org.springframework.stereotype.Service;
import es.uca.iw.model.Complaint;
import es.uca.iw.data.ComplaintRepository;
import es.uca.iw.data.UserRepository;
import es.uca.iw.model.User;


import java.util.List;

@Service
public class ComplaintService {
    private ComplaintRepository complaintRepository;

    public ComplaintService(ComplaintRepository complaintRepository, UserRepository userRepository) {
        this.complaintRepository = complaintRepository;
    }

    public List<Complaint> findAll() {
        return complaintRepository.findAll();
    }

    public List<Complaint> findByUser(User user) {
        return complaintRepository.findByUser(user);
    }
}