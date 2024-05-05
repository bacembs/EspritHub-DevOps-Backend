package tn.esprit.esprithub.services;

import tn.esprit.esprithub.entities.FreelanceJob;

import java.util.List;

public interface IFreelanceJobService {
    FreelanceJob createFreelanceJob(FreelanceJob freelanceJob);

    FreelanceJob getFreelanceJobById(Long jobId);

    List<FreelanceJob> getAllFreelanceJobs();

    FreelanceJob updateFreelanceJob(FreelanceJob freelanceJob);

    void deleteFreelanceJob(Long jobId);
}
