package tn.esprit.esprithub.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.esprithub.entities.Article;
import tn.esprit.esprithub.entities.Feedback;
import tn.esprit.esprithub.services.ArticleServices;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@AllArgsConstructor
@RequestMapping("/article")
public class ArticleRestController {
    private ArticleServices articleservice ;


    @GetMapping("/all")
    public List<Article> getAll(){
        return articleservice.getAll();
    }

}
