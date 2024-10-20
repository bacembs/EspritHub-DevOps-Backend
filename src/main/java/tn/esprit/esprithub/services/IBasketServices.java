package tn.esprit.esprithub.services;

import tn.esprit.esprithub.entities.Article;
import tn.esprit.esprithub.entities.User;

import java.util.List;
import java.util.Map;

public interface IBasketServices {

    public void ajouterArticle(Article article);
    public Double Total();
    public List<Article> getArticles();
    public void viderPanier();
    public void resetTotalArticlesParUtilisateur();
    Map<String, Double> TotalArticlesParUtilisateur();
    //public Double getTotalArticlesParUtilisateur(Long articleId);
}
