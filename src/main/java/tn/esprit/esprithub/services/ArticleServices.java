package tn.esprit.esprithub.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.esprithub.entities.Article;
import tn.esprit.esprithub.entities.Feedback;
import tn.esprit.esprithub.repository.IarticleRepository;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ArticleServices implements IarticleServices{
    private final IarticleRepository  articleRepository ;
    @Override
    public List<Article> getAll() {
        return (List< Article >) articleRepository.findAll();
    }

    @Override
    public Article getById(Long idArticle) {
        return      articleRepository.findById(idArticle).orElse(null);

    }


}
