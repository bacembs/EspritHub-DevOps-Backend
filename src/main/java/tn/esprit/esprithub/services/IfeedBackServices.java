package tn.esprit.esprithub.services;

import tn.esprit.esprithub.DTO.bannedUser;
import tn.esprit.esprithub.entities.Feedback;
import tn.esprit.esprithub.entities.Transaction;
import tn.esprit.esprithub.entities.User;

import java.util.List;

public interface IfeedBackServices {




    boolean  addFeedback(Feedback Feedback);
    Feedback updateFeedback(Feedback Feedback);
    public boolean deleteFeedback(Long feedbackId) ;
    Feedback getById(Long numFeedback);
    List<Feedback> getAll();

    List<bannedUser> getAllbanned();

}
