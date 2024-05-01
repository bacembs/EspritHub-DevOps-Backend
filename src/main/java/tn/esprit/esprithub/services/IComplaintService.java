package tn.esprit.esprithub.services;

import tn.esprit.esprithub.entities.Complaint;

import java.util.List;

public interface IComplaintService {
    Complaint addComplaint(Complaint complaint);
    Complaint updateComplaint(Complaint complaint);
    void deleteComplaint(Long complaintId);
    Complaint getComplaintById(Long complaintId);
    List<Complaint> getAll();
}
