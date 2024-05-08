package tn.esprit.esprithub.controllers;




import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.esprithub.entities.Article;
import tn.esprit.esprithub.entities.Mycategory;
import tn.esprit.esprithub.entities.Mycondition;
import tn.esprit.esprithub.entities.User;
import tn.esprit.esprithub.repository.ArticleRepository;
import tn.esprit.esprithub.services.ArticleServices;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.esprithub.services.UserService;


@RestController
@AllArgsConstructor
@RequestMapping("/Articles")
public class
ArticleController {
    private ArticleServices articleServices;
    private ArticleRepository articlerepos;
    private UserService userService;

    @Autowired
    private ResourceLoader resourceLoader;
   /* @PostMapping("/addArticle")
    public void addArticle(@ModelAttribute Article article, @RequestParam("imgArticle") MultipartFile photoFile) {
        Long userId = 2L; // Utilisateur de test avec ID 2
        articleServices.addArticleWithPhoto(userId, article, photoFile);
    }*/


    // controller
    @PostMapping("/addArticle")
    public ResponseEntity<String> addArticle(@RequestParam("descriptionArticle") String description,
                                             @RequestParam("category") Mycategory categories,
                                             @RequestParam("nameArticle") String name,
                                             @RequestParam("priceArticle") float prix,
                                             @RequestParam("conditionArticle") Mycondition etat,
                                             @RequestParam("imgArticle") MultipartFile photoFile,
                                             @RequestParam("userId") Long userId) {

        Article article = new Article();
        article.setDescriptionArticle(description);
        article.setCategoryArticle(categories);
        article.setPriceArticle(prix);
        article.setConditionArticle(etat);
        article.setNameArticle(name);


        User user = new User();
        user.setUserId(userId);
        article.setUsers(user);

        articleServices.addArticleWithPhoto(userId, article, photoFile);

        return ResponseEntity.ok("Article ajouté avec succès");
    }






    @DeleteMapping("/delete/{articleID}")
    public void deleteArticle(@PathVariable Long articleID) {
        articleServices.deleteArticle(articleID);
    }


    @GetMapping("/categories")
    public List<String> getCategories() {
        return Arrays.asList("Technology", "Home", "Educational", "Wellbeing");
    }

    @GetMapping("/conditions")
    public List<String> getConditions() {
        return Arrays.asList("Good", "Fair", "Damaged");
    }
    private final String uploadPath = "C:\\Users\\Nouhe\\IdeaProjects\\EspritHub\\src\\main\\resources\\static\\photos\\";

    @GetMapping("/a/{userId}")
    public ResponseEntity<List<Article>> getAllArticles(@PathVariable Long userId) {
        List<Article> articles = articleServices.getArticless(userId);
        for (Article article : articles) {
            String imagePath = uploadPath + article.getImgArticle();
            try {
                byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                article.setImgArticle(base64Image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok(articles);
    }
    @GetMapping("/ar")
    public ResponseEntity<List<Article>> getAllArticles() {
        List<Article> articles = articleServices.getAll();
        for (Article article : articles) {
            String imagePath = uploadPath + article.getImgArticle();
            try {
                byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                article.setImgArticle(base64Image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok(articles);
    }


    @PutMapping("/updateArticle/{id}")
    public ResponseEntity<String> updateArticle(@PathVariable Long id,
                                                @RequestParam("nameArticle") String label,
                                                @RequestParam("descriptionArticle") String description,
                                                @RequestParam("category") Mycategory categories,
                                                @RequestParam("priceArticle") float prix,
                                                @RequestParam("conditionArticle") Mycondition etat,
                                                @RequestParam(value = "imgArticle", required = false) MultipartFile photoFile) {
        Article article = articleServices.getArticleById(id);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        article.setNameArticle(label);
        article.setDescriptionArticle(description);
        article.setCategoryArticle(categories);
        article.setPriceArticle(prix);
        article.setConditionArticle(etat);
        articleServices.updateArticleWithPhoto(article, photoFile);

        return ResponseEntity.ok("Article mis à jour avec succès");
    }


    @GetMapping("/articles/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        Optional<Article> optionalArticle = articlerepos.findById(id);
        if (optionalArticle.isPresent()) {
            return ResponseEntity.ok(optionalArticle.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/all-with-userids-and-usernames")
    public ResponseEntity<List<Object[]>> getAllArticlesWithUserIdsAndUsernames() {
        List<Object[]> articlesWithUserIdsAndUsernames = articleServices.findAllArticlesWithUserIdsAndUsernames();
        List<Object[]> response = new ArrayList<>();
        for (Object[] objects : articlesWithUserIdsAndUsernames) {
            Long userId = (Long) objects[6];
            String username = (String) objects[7];
            String fullName = userService.getUserFullName(userId);
            Object[] articleWithUsername = Arrays.copyOf(objects, objects.length + 1);
            articleWithUsername[objects.length] = fullName;
            response.add(articleWithUsername);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filtre")
    public List<Article> filterArticles(@RequestParam(value = "category", required = false) Mycategory category,
                                        @RequestParam(value = "condition", required = false) Mycondition condition,
                                        @RequestParam(value = "price", required = false) Float price




    ) {
        return articleServices.filterArticles(category, condition, price);
    }

    @GetMapping("/maxprice")
    public Float findMaxPrice(){
        return articleServices.findMaxPrice();
    }


    @GetMapping("/minprice")
    public Float findMinPrice(){
        return articleServices.findMinPrice();
    }


    @GetMapping("/getUserIdByName")
    public Long getUserIdByName(@RequestParam String username) {
        return userService.getUserIdByName(username);
    }


    @GetMapping("/categoriesCount")
    public ResponseEntity<List<Object[]>> getCategoriesCount() {
        List<Object[]> categoryCounts = articleServices.getCategoriesCount();
        return ResponseEntity.ok(categoryCounts);
    }
    @GetMapping("/countByCondition")
    public ResponseEntity<List<Object[]>> countByCondition() {
        List<Object[]> result = articleServices.countByCondition();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/countByCategoryAndCondition")
    public ResponseEntity<List<Object[]>> countByCategoryAndCondition() {
        List<Object[]> result = articleServices.countByCategoryAndCondition();
        return ResponseEntity.ok(result);
    }

   /* @GetMapping("/all")
    public List<Article> getAll(){
        return articleServices.getAll();
    }*/

@GetMapping("articlesByuser/{userId}")
public List<Article> findByUserId(@PathVariable Long userId){
        return articleServices.getByUserId(userId);
}
}