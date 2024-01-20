package es.uca.iw.services;

import es.uca.iw.data.ComplaintRepository;
import es.uca.iw.data.UserRepository;
import es.uca.iw.model.Complaint;
import es.uca.iw.model.User;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;



    @Autowired
    public ComplaintService(ComplaintRepository complaintRepository, UserRepository userRepository) {
        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
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

    private String getUserEmail(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(User::getEmail).orElse(null);
    }

    public void addComentariosAReclamacion(Complaint complaint, String nuevoComentario) {
        List<String> comentariosActuales = complaint.getComments();
        comentariosActuales.add(nuevoComentario);
        complaint.setComments(comentariosActuales);
        complaintRepository.save(complaint);

        String userEmail = getUserEmail(complaint.getUserId());
        if (userEmail != null) {
            try {
                EmailService.sendComentarioEmail(userEmail, nuevoComentario);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Complaint> getComplaintsByAuthenticatedUser() {
        User authenticatedUser = getAuthenticatedUser();
        return complaintRepository.findByUser(authenticatedUser);
    }

    public void cambiarEstadoReclamacion(Long reclamacionId, Complaint.ComplaintStatus nuevoEstado) {
        Optional<Complaint> optionalReclamacion = complaintRepository.findById(reclamacionId);
        optionalReclamacion.ifPresent(reclamacion -> {
            reclamacion.setStatus(nuevoEstado);
            complaintRepository.save(reclamacion);
        });
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