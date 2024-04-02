package tn.esprit.esprithub.controllers;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.esprithub.entities.FreelanceJob;
import tn.esprit.esprithub.services.FreelanceJobService;

import java.util.List;

@RestController
@RequestMapping("/freelance-jobs")
public class FreelanceJobController {
        private final FreelanceJobService freelanceJobService;

        @Autowired
        public FreelanceJobController(FreelanceJobService freelanceJobService) {
            this.freelanceJobService = freelanceJobService;
        }

        @GetMapping
        public List<FreelanceJob> getAllFreelanceJobs() {

            return freelanceJobService.getAllFreelanceJobs();
        }

        @GetMapping("/{id}")
        public FreelanceJob getFreelanceJobById(@PathVariable Long id) {
            return freelanceJobService.getFreelanceJobById(id);
        }

    @PostMapping
    public FreelanceJob createFreelanceJob(@RequestBody FreelanceJob freelanceJob) {
        return freelanceJobService.saveFreelanceJob(freelanceJob);
    }

    @PutMapping("/{id}")
    public FreelanceJob updateFreelanceJob(@PathVariable Long id, @RequestBody FreelanceJob updatedFreelanceJob) {
        FreelanceJob freelanceJob = freelanceJobService.getFreelanceJobById(id);
        if (freelanceJob != null) {
            BeanUtils.copyProperties(updatedFreelanceJob, freelanceJob, "jobId");
            return freelanceJobService.saveFreelanceJob(freelanceJob);
        } else {
            throw new IllegalArgumentException("Freelance job not found with id: " + id);
        }
    }

    @DeleteMapping("/{id}")
        public void deleteFreelanceJob(@PathVariable Long id) {
            freelanceJobService.deleteFreelanceJob(id);
        }
}
