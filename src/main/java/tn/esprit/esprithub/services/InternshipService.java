package tn.esprit.esprithub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.esprithub.entities.Filee;
import tn.esprit.esprithub.entities.Internship;
import tn.esprit.esprithub.repository.FileRepository;
import tn.esprit.esprithub.repository.InternshipRepository;

import java.util.List;

@Service
public class InternshipService implements IInternshipService {

    private final InternshipRepository internshipRepository;
    @Autowired
    FileRepository fileRepository;

    @Autowired
    public InternshipService(InternshipRepository internshipRepository) {
        this.internshipRepository = internshipRepository;
    }

    @Override
    public Internship createInternship(Internship internship) {
        return internshipRepository.save(internship);
    }

    @Override
    public Internship getInternshipById(Long internshipId) {
        return internshipRepository.findById(internshipId).orElse(null);
    }

    @Override
    public List<Internship> getAllInternships() {
        return internshipRepository.findAll();
    }

    @Override
    public Internship updateInternship(Internship internship) {
        return internshipRepository.save(internship);
    }

    @Override
    public boolean deleteInternship(Long internshipId) {
        internshipRepository.deleteById(internshipId);
        return true;
    }

    @Override
    public void saveFile(Filee file) {
        fileRepository.save(file);
    }

    @Override
    public Filee getFileById(long fileId) {
        return fileRepository.findById(fileId).orElse(null);
    }

    @Override
    public void updateFile(Filee file) {
        fileRepository.save(file);
    }

    @Override
    public void deleteFile(long fileId) {
        fileRepository.deleteById(fileId);
    }

    @Override
    public List<Filee> getAllFiles() {
        return null;
    }
}