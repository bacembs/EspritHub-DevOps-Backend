package tn.esprit.esprithub.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.esprithub.entities.Internship;
import tn.esprit.esprithub.services.InternshipService;

import java.util.List;

@RestController
@RequestMapping("/internships")
public class InternshipController {
    private final InternshipService internshipService;

    @Autowired
    public InternshipController(InternshipService internshipService) {
        this.internshipService = internshipService;
    }

    @GetMapping
    public List<Internship> getAllInternships() {
        return internshipService.getAllInternships();
    }

    @GetMapping("/{id}")
    public Internship getInternshipById(@PathVariable Long id) {
        return internshipService.getInternshipById(id);
    }

    @PostMapping
    public Internship createInternship(@RequestBody Internship internship) {
        return internshipService.saveInternship(internship);
    }
    @PutMapping("/{id}")
    public Internship updateInternship(@PathVariable Long id, @RequestBody Internship updatedInternship) {
        Internship internship = internshipService.getInternshipById(id);
        if (internship != null) {
            internship.setTitleInternship(updatedInternship.getTitleInternship());
            internship.setCompanyInternship(updatedInternship.getCompanyInternship());
            return internshipService.saveInternship(internship);
        } else {
            throw new IllegalArgumentException("Internship not found with id: " + id);
        }
    }
    @DeleteMapping("/{id}")
    public void deleteInternship(@PathVariable Long id) {
        internshipService.deleteInternship(id);
    }
}
