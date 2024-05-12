package tn.esprit.esprithub.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;


import tn.esprit.esprithub.entities.Article;
import tn.esprit.esprithub.entities.Mycategory;
import tn.esprit.esprithub.entities.Mycondition;
import tn.esprit.esprithub.entities.User;
import tn.esprit.esprithub.repository.ArticleRepository;
import tn.esprit.esprithub.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ArticleServices implements IArticleServices {
    private ArticleRepository articlerepos;
    private UserRepository userrepos;


    @Override
    public void addArticleWithPhoto(Long userId, Article article, MultipartFile photoFile) {
        String uploadPath = "./src/main/resources/static/photos/";

        try {
            byte[] bytes = photoFile.getBytes();
            String fileName = photoFile.getOriginalFilename();
            Path path = Paths.get(uploadPath + fileName);
            Files.write(path, bytes);
            article.setImgArticle(fileName);

            articlerepos.save(article);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void deleteArticle(Long articleID) {
        articlerepos.deleteById(articleID);
    }


    @Override
    public List<Article> getAllArticles() {
        return articlerepos.findAll().stream()
                .filter(article -> article.getTransactions() == null || article.getTransactions().getTransactionId() == null)
                .collect(Collectors.toList());
    }


    @Override
    public Article getArticleById(Long id) {
        Optional<Article> optionalArticle = articlerepos.findById(id);
        return optionalArticle.orElse(null);
    }

    @Override
    public void updateArticleWithPhoto(Article article, MultipartFile photoFile) {
        if (photoFile != null && !photoFile.isEmpty()) {
            String fileName = photoFile.getOriginalFilename();
            String filePath = "./src/main/resources/static/photos/" + fileName;
            try {
                File file = new File(filePath);
                photoFile.transferTo(file);
                article.setImgArticle(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        articlerepos.save(article);
    }

    @Override
    public List<Object[]> findAllArticlesWithUserIdsAndUsernames() {
        return articlerepos.findAllArticlesWithUserIdsAndUsernames();
    }

    @Override
    public List<Article> filterArticles(Mycategory category, Mycondition condition, Float price) {
        if (category != null && condition != null && price != null) {
            return articlerepos.findByCategoryArticleAndConditionArticleAndPriceArticle(category, condition, price);
        } else if (category != null && condition != null) {
            return articlerepos.findByCategoryArticleAndConditionArticle(category, condition);
        } else if (category != null) {
            return articlerepos.findByCategoryArticle(category);
        } else if (condition != null) {
            return articlerepos.findByConditionArticle(condition);
        } else if (price != null) {
            return articlerepos.findByPriceArticle(price);
        } else {
            return articlerepos.findAll();
        }
    }

    @Override
    public Float findMaxPrice() {
        return articlerepos.findMaxPrice();
    }

    @Override
    public Float findMinPrice() {
        return articlerepos.findMinPrice();
    }


    @Override
    public List<Object[]> getCategoriesCount() {
        return articlerepos.countArticlesByCategory();
    }

    @Override
    public List<Object[]> countByCondition() {
        return articlerepos.countByCondition();
    }

    @Override
    public List<Object[]> countByCategoryAndCondition(){
        return articlerepos.countByCategoryAndCondition();

    }

    @Override
    public List<Article> getArticless(long user){
        return (List< Article >) articlerepos.getArticless(user);
    }
    @Override
    public List<Article> getAll(){
        return (List< Article >) articlerepos.findAll();
    }


    @Override
    public Article getById(Long idArticle) {
        return      articlerepos.findById(idArticle).orElse(null);

    }

@Override
 public List<Article> getByUserId(Long userId){
        return articlerepos.getByUserId(userId);
}
}