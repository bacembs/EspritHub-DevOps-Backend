package tn.esprit.esprithub.services;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tn.esprit.esprithub.entities.Complaint;
import tn.esprit.esprithub.repositories.IComplaintRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class ComplaintService implements IComplaintService {
    private IComplaintRepository complaintRepository;
    @Override
    public Complaint addComplaint(Complaint complaint) {
        return complaintRepository.save(complaint);
    }

    @Override
    public Complaint updateComplaint(Complaint complaint) {
        return complaintRepository.save(complaint);
    }

    @Override
    public void deleteComplaint(Long complaintId) {
        complaintRepository.deleteById(complaintId);
    }

    @Override
    public Complaint getComplaintById(Long complaintId) {
        return complaintRepository.findById(complaintId).orElse(null);
    }

    @Override
    public List<Complaint> getAll() {
        return (List<Complaint>) complaintRepository.findAll();
    }
}