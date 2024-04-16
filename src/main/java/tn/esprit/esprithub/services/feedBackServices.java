package tn.esprit.esprithub.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.esprithub.entities.Feedback;
import tn.esprit.esprithub.repository.IfeedBackRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class feedBackServices implements IfeedBackServices {
    private final IfeedBackRepository feedBackRepository;


    @Override
    public boolean addFeedback(Feedback feedback) {
        if (feedback.getCommentFeedback() == null || feedback.getCommentFeedback().isEmpty()) {
            return false;
        }

        feedBackRepository.save(feedback);

        return true;
    }

    @Override
    public Feedback updateFeedback(Feedback Feedback) {
        return feedBackRepository.save(Feedback);
    }

    @Override
    public boolean deleteFeedback(Long feedbackId) {
        Feedback feedbackOptional = feedBackRepository.findById(feedbackId).orElse(null);

        if (feedbackOptional!=null) {
            feedBackRepository.deleteById(feedbackId);
            return true; // La suppression s'est déroulée avec succès
        } else {
            return false; // L'élément n'existe pas, la suppression est impossible
        }
    }


    @Override
    public Feedback getById(Long numFeedback) {
        return
                feedBackRepository.findById(numFeedback).orElse(null);
    }

    @Override
    public List<Feedback> getAll() {
        return (List<Feedback>) feedBackRepository.findAll();
    }
}
