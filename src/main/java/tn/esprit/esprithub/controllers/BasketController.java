package tn.esprit.esprithub.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.esprithub.entities.Article;
import tn.esprit.esprithub.entities.User;
import tn.esprit.esprithub.repository.ArticleRepository;
import tn.esprit.esprithub.services.BasketServices;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor

public class BasketController {
    private BasketServices basketServices;
    private ArticleRepository articlerepos;

    @PostMapping("/ajouter/{articleId}")
    public ResponseEntity<String> ajouterArticleAuPanier(@PathVariable Long articleId) {
        Optional<Article> articleOptional = articlerepos.findById(articleId);
        if (articleOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Article article = articleOptional.get();
        basketServices.ajouterArticle(article);
        return ResponseEntity.ok("Article ajouté au panier");
    }

    @GetMapping("/total")
    public ResponseEntity<Double> getTotalPanier() {
        return ResponseEntity.ok(basketServices.Total());
    }

    @GetMapping("/art")
    public ResponseEntity<List<Article>> getArticlesPanier() {
        return ResponseEntity.ok(basketServices.getArticles());
    }

    @PostMapping("/vider")
    public ResponseEntity<String> viderPanier() {
        basketServices.viderPanier();
        return ResponseEntity.ok("Panier vidé");
    }



}
