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
    public Feedback addFeedback(Feedback Feedback) {
        return feedBackRepository.save(Feedback);
    }

    @Override
    public Feedback updateFeedback(Feedback Feedback) {
        return feedBackRepository.save(Feedback);
    }

    @Override
    public void deleteFeedback(Long Feedback) {
feedBackRepository.deleteById(Feedback);
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
