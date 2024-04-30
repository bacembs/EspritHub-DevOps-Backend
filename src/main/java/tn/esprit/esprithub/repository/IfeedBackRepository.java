package tn.esprit.esprithub.repository;


import org.springframework.data.repository.CrudRepository;
import tn.esprit.esprithub.entities.Feedback;

import java.util.List;

public interface IfeedBackRepository extends CrudRepository<Feedback, Long> {
    List<Feedback> findAllByCommentFeedback(String commentaire);
}
