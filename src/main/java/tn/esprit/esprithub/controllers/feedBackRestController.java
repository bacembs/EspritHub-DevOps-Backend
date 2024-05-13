package tn.esprit.esprithub.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.esprithub.DTO.bannedUser;
import tn.esprit.esprithub.entities.Feedback;
import tn.esprit.esprithub.entities.Transaction;
import tn.esprit.esprithub.entities.User;
import tn.esprit.esprithub.services.IfeedBackServices;
import java.util.List;


@RestController
//@CrossOrigin(origins = "*")

@AllArgsConstructor
@RequestMapping("/feedBack")
public class feedBackRestController {
    private IfeedBackServices serviceFeedBack ;


    @GetMapping("/badfeedback")
    public ResponseEntity<List<bannedUser>> getAlluserWithbadFeedback() {

        List<bannedUser> users = serviceFeedBack.getAllbanned();



        return ResponseEntity.ok(users);
    }
    @PostMapping("/add")
    public boolean addFeedback(@RequestBody Feedback Feedback){
        return serviceFeedBack.addFeedback(Feedback);
    }
    @PutMapping("/update")
    public Feedback updateFeedback(@RequestBody Feedback Feedback){
        return serviceFeedBack.updateFeedback(Feedback);
    }

    @GetMapping("/get/{numFeedback}")
    public Feedback getFeedback(@PathVariable Long numFeedback){
        return serviceFeedBack.getById(numFeedback);
    }

    @DeleteMapping("/delete/{numFeedback}")
    public ResponseEntity<Boolean> deleteFeedback(@PathVariable Long numFeedback) {
        boolean isDeleted = serviceFeedBack.deleteFeedback(numFeedback);

        if (isDeleted) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        List<Feedback> feedbacks = serviceFeedBack.getAll();

        if (feedbacks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null); // Ou un message d'erreur approprié dans le corps de la réponse
        }

        return ResponseEntity.ok(feedbacks);
    }

}




