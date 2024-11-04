package tn.esprit.esprithub.services;

import org.springframework.stereotype.Service;
import tn.esprit.esprithub.entities.Article;
import tn.esprit.esprithub.entities.User;
import tn.esprit.esprithub.repository.ArticleRepository;

import java.util.*;


@Service
public class BasketServices implements IBasketServices{

    private ArticleRepository articlerepos;
    private List<Article> articles = new ArrayList<>();
    private Map<String, Double> totalArticlesParUtilisateur = new HashMap<>();
@Override
    public void ajouterArticle(Article article) {
        articles.add(article);
    }
    @Override
    public Double Total() {
        double total = 0.0;
        for (Article article : articles) {
            total = total + article.getPriceArticle();
        }
        return total;
    }

    @Override
    public Map<String, Double> TotalArticlesParUtilisateur() {
        Map<String, Double> tempTotalArticlesParUtilisateur = new HashMap<>();
        for (Article article : articles) {
            User user = article.getUsers();
            String nomUtilisateur = user.getFirstName();
            Double total = tempTotalArticlesParUtilisateur.getOrDefault(nomUtilisateur, 0.0);
            total = total + article.getPriceArticle();
            tempTotalArticlesParUtilisateur.put(nomUtilisateur, total);
        }
        return tempTotalArticlesParUtilisateur;
    }


@Override
    public List<Article> getArticles() {
        return new ArrayList<>(articles);
    }
    @Override
    public void resetTotalArticlesParUtilisateur() {
        totalArticlesParUtilisateur.clear();
    }





@Override
    public void viderPanier() {
        resetTotalArticlesParUtilisateur();
        articles.clear();
        articles = new ArrayList<>();
    }


/*@Override
    public Double getTotalArticlesParUtilisateur(Long articleId) {
        Article article = articlerepos.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        User user = article.getUsers();
        List<Article> articlesUtilisateur = articlerepos.findByUser(user);
        Double total = calculateTotal(articlesUtilisateur);
        return total;
    }*/

    private Double calculateTotal(List<Article> articles) {
        return articles.stream().mapToDouble(Article::getPriceArticle).sum();
    }



}





