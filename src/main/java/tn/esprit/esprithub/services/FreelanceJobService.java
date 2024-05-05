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
public class FreelanceJobService implements IFreelanceJobService{
    @Autowired

    private final FreelanceJobRepository freelanceJobRepository;

    @Autowired
    public FreelanceJobService(FreelanceJobRepository freelanceJobRepository) {
        this.freelanceJobRepository = freelanceJobRepository;
    }

    @Override
    public FreelanceJob createFreelanceJob(FreelanceJob freelanceJob) {
        return freelanceJobRepository.save(freelanceJob);
    }

    @Override
    public FreelanceJob getFreelanceJobById(Long jobId) {
        return freelanceJobRepository.findById(jobId).orElse(null);
    }

    @Override
    public List<FreelanceJob> getAllFreelanceJobs() {
        return freelanceJobRepository.findAll();
    }

    @Override
    public FreelanceJob updateFreelanceJob(FreelanceJob freelanceJob) {
        return freelanceJobRepository.save(freelanceJob);
    }

    @Override
    public void deleteFreelanceJob(Long jobId) {
        freelanceJobRepository.deleteById(jobId);
    }
}
