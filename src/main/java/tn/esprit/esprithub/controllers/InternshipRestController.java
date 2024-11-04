package tn.esprit.esprithub.controllers;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.esprithub.entities.Filee;
import tn.esprit.esprithub.entities.Internship;
import tn.esprit.esprithub.repository.FileRepository;
import tn.esprit.esprithub.services.IUserService;
import tn.esprit.esprithub.services.InternshipService;
import tn.esprit.esprithub.services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/internships")
//@CrossOrigin(origins = "*")

public class InternshipRestController {

    @Autowired
    private final InternshipService internshipService;
    @Autowired
    private final FileRepository fileRepo;
    @Autowired
    private final JavaMailSender mailSender;
    @Autowired
    private final UserService userservice;

    @Autowired
    public InternshipRestController(InternshipService internshipService, FileRepository fileRepo, JavaMailSender mailSender, UserService userservice) {
        this.internshipService = internshipService;
        this.fileRepo = fileRepo;
        this.mailSender = mailSender;


        this.userservice = userservice;
    }

    @PostMapping("/createInternship/{userId}")
    public ResponseEntity<Internship> createInternship(@PathVariable Long userId,@RequestBody Internship internship) {
        Internship createdInternship = internshipService.createInternship(userId,internship);
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


    @GetMapping("/test/{Id}")
    public Set<Internship> recommandation(@PathVariable Long Id) {
        return internshipService.recommendation(Id);
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

   /* @PostMapping("/addfile")
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
    }*/

    @PostMapping("/addFile/{internshipId}/{userId}")
    public ResponseEntity<?> addFile(@PathVariable("internshipId") Long internshipId,@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        try {

            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaa");
            Internship internship = internshipService.getInternshipById(internshipId);
            if (internship == null) {
                return ResponseEntity.notFound().build();
            }

            Filee fileEntity = new Filee();
            fileEntity.setFilename(file.getOriginalFilename());
            fileEntity.setContentType(file.getContentType());
            fileEntity.setData(file.getBytes());
            fileEntity.setInternship(internship); // Associer le stage au fichier
            fileEntity.setUser(userservice.getUserById(userId));
            System.out.println("aaaaaa"+fileEntity.toString());

            fileRepo.save(fileEntity);

            // Envoyer l'e-mail
            String toEmail = "destinataire@example.com";
            String subject = "Nouveau fichier téléchargé     ";
            String body = "Un nouveau fichier a été téléchargé. voici le lien rejoudre le pour faire un entretien  http://localhost:4200/internshipRoom/1";

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("arij.khedhira@esprit.tn");
            message.setTo(toEmail);
            message.setText(body);
            message.setSubject(subject);
            mailSender.send(message);

            String response = "Fichier téléchargé avec succès ! E-mail envoyé.";
            HttpStatus httpStatus = HttpStatus.CREATED;
            return new ResponseEntity<>(response, httpStatus);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<Filee>> getFile() {
        List<Filee> files = fileRepo.findAll();
        return ResponseEntity.ok(files);
    }

    @DeleteMapping("/deleteFile/{id}")
    public void deleteFilee(@PathVariable("id") long fileID) {
        internshipService.deleteFile(fileID);
    }

    ////////////////////////mail///////////////////////

    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendSimpleEmail(@RequestParam("toEmail") String toEmail, @RequestParam("subject") String subject, @RequestParam("body") String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("arij.khedhira@esprit.tn");
            message.setTo(toEmail);
            message.setText(body);
            message.setSubject(subject);
            mailSender.send(message);

            String response = "Email sent successfully!";
            HttpStatus httpStatus = HttpStatus.OK;
            return new ResponseEntity<>(response, httpStatus);
        } catch (Exception e) {
            String error = "Failed to send email.";
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(error, httpStatus);
        }
    }
}




