package tn.esprit.esprithub.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.esprithub.entities.Article;
import tn.esprit.esprithub.entities.Transaction;
import tn.esprit.esprithub.entities.User;
import tn.esprit.esprithub.services.ArticleServices;
import tn.esprit.esprithub.services.UserServices;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@AllArgsConstructor
@RequestMapping("/user")
public class userRestController {

    private UserServices servicesuser ;


    @GetMapping("/user")
    public User getbyuser(@RequestBody User user){
        return servicesuser.getByNom(user.getUsername());
    }

}


