package tn.esprit.esprithub.services;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import tn.esprit.esprithub.entities.FreelanceJob;
import tn.esprit.esprithub.repository.FreelanceJobRepository;

import java.util.List;

@Service
public class FreelanceJobService {
    private final FreelanceJobRepository freelanceJobRepository;

    @Autowired
    public FreelanceJobService(FreelanceJobRepository freelanceJobRepository) {
        this.freelanceJobRepository = freelanceJobRepository;
    }

    public List<FreelanceJob> getAllFreelanceJobs() {
        return freelanceJobRepository.findAll();
    }

    public FreelanceJob getFreelanceJobById(Long id) {
        return freelanceJobRepository.findById(id).orElse(null);
    }

    public FreelanceJob saveFreelanceJob(FreelanceJob freelanceJob) {
        return freelanceJobRepository.save(freelanceJob);
    }

    public void deleteFreelanceJob(Long id) {
        freelanceJobRepository.deleteById(id);
    }

}
