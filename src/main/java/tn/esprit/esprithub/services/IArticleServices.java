package tn.esprit.esprithub.services;

import tn.esprit.esprithub.entities.Article;
import tn.esprit.esprithub.entities.Feedback;

import java.util.List;

public interface IarticleServices {
    List<Article> getAll();
    Article getById(Long idArticle);


}
