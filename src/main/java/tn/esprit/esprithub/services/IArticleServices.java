package tn.esprit.esprithub.services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.esprithub.entities.Article;
import tn.esprit.esprithub.entities.Mycategory;
import tn.esprit.esprithub.entities.Mycondition;
import tn.esprit.esprithub.entities.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IArticleServices {
    //public void addArticleWithPhoto(Article article, MultipartFile photoFile);
    public void addArticleWithPhoto(Long userId, Article article, MultipartFile photoFile);
    public void deleteArticle(Long articleID);
    public List<Article> getAllArticles();
    public Article getArticleById(Long id);
    public void updateArticleWithPhoto(Article article, MultipartFile photoFile);
    public List<Object[]> findAllArticlesWithUserIdsAndUsernames();
    public List<Article> filterArticles(Mycategory category, Mycondition condition, Float price);
    public Float findMaxPrice();
    public Float findMinPrice();

    public List<Article> getAll();

    public Article getById(Long idArticle);

    public List<Object[]> getCategoriesCount();

    public List<Object[]> countByCondition();
    public List<Object[]> countByCategoryAndCondition();

}

