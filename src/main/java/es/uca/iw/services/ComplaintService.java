package es.uca.iw.services;

import es.uca.iw.data.ComplaintRepository;
import es.uca.iw.model.Complaint;
import es.uca.iw.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;

    public ComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    public List<Complaint> getComplaints() {
        return complaintRepository.findAll();
    }

    public List<Complaint> getComplaintsByUser(User user) {
        return complaintRepository.findByUser(user);
    }

    public void saveComplaint(Complaint complaint) {
        complaintRepository.save(complaint);
    }

    public int getNextComplaintId() {
        // Implementa lógica para obtener el siguiente ID, por ejemplo, consulta al repositorio
        // Aquí solo devuelvo 0 como ejemplo
        return 0;
    }

    // Agregado para cumplir con la sugerencia anterior
    public List<Complaint> findAll() {
        return complaintRepository.findAll();
    }
}