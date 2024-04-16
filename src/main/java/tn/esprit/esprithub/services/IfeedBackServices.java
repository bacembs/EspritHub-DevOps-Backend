package tn.esprit.esprithub.services;

import tn.esprit.esprithub.entities.Feedback;

import java.util.List;

public interface IfeedBackServices {




    boolean  addFeedback(Feedback Feedback);
    Feedback updateFeedback(Feedback Feedback);
    public boolean deleteFeedback(Long feedbackId) ;
    Feedback getById(Long numFeedback);
    List<Feedback> getAll();
}
