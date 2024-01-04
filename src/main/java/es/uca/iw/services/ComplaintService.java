package es.uca.iw.services;

import es.uca.iw.data.ComplaintRepository;
import es.uca.iw.model.Complaint;
import es.uca.iw.model.User;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;

    public ComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    public Complaint addComplaint(Complaint complaint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        complaint.setUser(user);
        complaint.setStatus(Complaint.ComplaintStatus.EN_ESPERA);
        complaint.setCreationDate(LocalDate.now());
        Complaint savedComplaint = complaintRepository.save(complaint);
        return savedComplaint;
    }

    public void addComentariosAReclamacion(Complaint complaint, String nuevoComentario) {
        List<String> comentariosActuales = complaint.getComments();
        comentariosActuales.add(nuevoComentario);
        complaint.setComments(comentariosActuales);
        complaintRepository.save(complaint);
    }  

    public List<Complaint> getComplaintsByAuthenticatedUser() {
        User authenticatedUser = getAuthenticatedUser();
        return complaintRepository.findByUser(authenticatedUser);
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

    public void eliminarReclamacion(Long id) {
        complaintRepository.deleteById(id);
    }

    public List<Complaint> findAll() {
        return complaintRepository.findAll();
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}