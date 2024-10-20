package tn.esprit.esprithub.repository;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.esprithub.entities.Article;
import tn.esprit.esprithub.entities.Transaction;

public interface IarticleRepository  extends CrudRepository<Article, Long> {
}
