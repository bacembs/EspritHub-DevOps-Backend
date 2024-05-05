package tn.esprit.esprithub.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.esprithub.entities.FreelanceJob;
import tn.esprit.esprithub.services.IFreelanceJobService;

import java.util.List;
@RestController
@RequestMapping("/freelance")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class FreelanceJobRestController {
    private IFreelanceJobService freelanceJobService;

    @PostMapping("/addfreelance")
    public FreelanceJob addFreelanceJob(@RequestBody FreelanceJob freelanceJob) {
        return freelanceJobService.createFreelanceJob(freelanceJob);
    }

    @PutMapping("/updatefreelance")
    public FreelanceJob updateFreelanceJob(@RequestBody FreelanceJob freelanceJob) {
        return freelanceJobService.updateFreelanceJob(freelanceJob);
    }

    @GetMapping("/getfreelance/{jobId}")
    public ResponseEntity<FreelanceJob> getFreelanceJob(@PathVariable Long jobId) {
        FreelanceJob freelanceJob = freelanceJobService.getFreelanceJobById(jobId);
        if (freelanceJob != null) {
            return ResponseEntity.ok(freelanceJob);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deletefreelance/{jobId}")
    public ResponseEntity<Void> removeFreelanceJob(@PathVariable Long jobId) {
        freelanceJobService.deleteFreelanceJob(jobId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/allfreelance")
    public List<FreelanceJob> getAllFreelanceJobs() {
        return freelanceJobService.getAllFreelanceJobs();
    }


}