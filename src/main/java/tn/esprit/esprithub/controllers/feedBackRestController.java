package tn.esprit.esprithub.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.esprithub.entities.Feedback;
import tn.esprit.esprithub.services.IfeedBackServices;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/feedBack")
public class feedBackRestController {
    private IfeedBackServices serviceFeedBack ;
    @PostMapping("/add")
    public Feedback addFeedback(@RequestBody Feedback Feedback){
        return serviceFeedBack.addFeedback(Feedback);
    }
    @PutMapping("/update")
    public Feedback updateFeedback(@RequestBody Feedback Feedback){
        return serviceFeedBack.updateFeedback(Feedback);
    }

    @GetMapping("/get/{numSkieur}")
    public Feedback getFeedback(@PathVariable Long numFeedback){
        return serviceFeedBack.getById(numFeedback);
    }

    @DeleteMapping("/delete/{numSkieur}")
    public void deleteFeedback(@PathVariable Long numFeedback){
        serviceFeedBack.deleteFeedback(numFeedback);
    }

    @GetMapping("/all")
    public List<Feedback> getAll(){
        return serviceFeedBack.getAll();
    }

}
