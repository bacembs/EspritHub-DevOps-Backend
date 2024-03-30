package tn.esprit.esprithub.services;

import tn.esprit.esprithub.entities.Feedback;

import java.util.List;

public interface IfeedBackServices {




    Feedback  addFeedback(Feedback Feedback);
    Feedback updateFeedback(Feedback Feedback);
    void deleteFeedback(Long Feedback);
    Feedback getById(Long numFeedback);
    List<Feedback> getAll();
}
