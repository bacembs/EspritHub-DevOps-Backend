package tn.esprit.esprithub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.esprithub.entities.Filee;
import tn.esprit.esprithub.entities.Internship;
import tn.esprit.esprithub.repository.FileRepository;
import tn.esprit.esprithub.services.InternshipService;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/internships")
@CrossOrigin(origins = "http://localhost:4200")

public class InternshipRestController {

    @Autowired
    private final InternshipService internshipService;
    @Autowired
    private final FileRepository fileRepo;


    @Autowired
    public InternshipRestController(InternshipService internshipService, FileRepository fileRepo) {
        this.internshipService = internshipService;
        this.fileRepo = fileRepo;
    }

    @PostMapping("/createInternship")
    public ResponseEntity<Internship> createInternship(@RequestBody Internship internship) {
        Internship createdInternship = internshipService.createInternship(internship);
        return ResponseEntity.ok(createdInternship);
    }

    @GetMapping("/getInternshipById/{internshipId}")
    public ResponseEntity<Internship> getInternshipById(@PathVariable Long internshipId) {
        Internship internship = internshipService.getInternshipById(internshipId);
        if (internship != null) {
            return ResponseEntity.ok(internship);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAllInternship")
    public List<Internship> getAllInternships() {
        return internshipService.getAllInternships();
    }

    @PutMapping("/updateInternship/{internshipId}")
    public ResponseEntity<Internship> updateInternship(@PathVariable Long internshipId, @RequestBody Internship internship) {
        Internship existingInternship = internshipService.getInternshipById(internshipId);
        if (existingInternship != null) {
            internship.setInternshipId(internshipId);
            Internship updatedInternship = internshipService.updateInternship(internship);
            return ResponseEntity.ok(updatedInternship);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteInternship/{internshipId}")
    public ResponseEntity<?> deleteInternship(@PathVariable Long internshipId) {
        boolean deleted = internshipService.deleteInternship(internshipId);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /////////////////////////////////* file controller *///////////////////////////

    @PostMapping("/addfile")
    public ResponseEntity<?> addFile(@RequestParam("file") MultipartFile file) {
        try {
            Filee fileEntity = new Filee();
            fileEntity.setFilename(file.getOriginalFilename());
            fileEntity.setContentType(file.getContentType());
            fileEntity.setData(file.getBytes());
            fileRepo.save(fileEntity);

            String message = "File added and uploaded successfully!";
            HttpStatus httpStatus = HttpStatus.CREATED;
            return new ResponseEntity<>(message, httpStatus);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

  /*  @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            Filee fileEntity = new  Filee();
            fileEntity.setFilename(file.getOriginalFilename());
            fileEntity.setContentType(file.getContentType());
            fileEntity.setData(file.getBytes());
            fileRepo.save(fileEntity);
            String message = "File uploaded successfully!";
            HttpStatus httpStatus = HttpStatus.CREATED;
            return new ResponseEntity<>(message, httpStatus);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }*/

    @GetMapping("/files")
    public ResponseEntity<List<Filee>> getFile() {
        List<Filee> files = fileRepo.findAll();
        return ResponseEntity.ok(files);
    }

    /*@DeleteMapping("/deleteFile/{id}")
    public void deleteFilee(@PathVariable("id") long fileID) {
        courseService.deleteFilee(fileID);
    }*/
}