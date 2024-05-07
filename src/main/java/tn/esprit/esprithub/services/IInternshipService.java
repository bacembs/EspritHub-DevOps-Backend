package tn.esprit.esprithub.services;

import tn.esprit.esprithub.entities.Filee;
import tn.esprit.esprithub.entities.Internship;

import java.util.Date;
import java.util.List;

public interface IInternshipService {
    Internship createInternship(Long userId,Internship internship);

    Internship getInternshipById(Long internshipId);

    List<Internship> getAllInternships();

    Internship updateInternship(Internship internship);

    boolean deleteInternship(Long internshipId);

    /////////////////////////////////////
    void saveFile(Filee file);

    Filee getFileById(long fileId);

    void updateFile(Filee file);

    void deleteFile(long fileId);

    List<Filee> getAllFiles();

    public void sendSimpleEmail(String toEmail,String subject,String body);
}