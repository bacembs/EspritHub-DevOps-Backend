package tn.esprit.esprithub.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.esprithub.DTO.bannedUser;
import tn.esprit.esprithub.entities.Feedback;
import tn.esprit.esprithub.entities.Transaction;
import tn.esprit.esprithub.entities.User;
import tn.esprit.esprithub.repository.IfeedBackRepository;
import tn.esprit.esprithub.repository.ItransactionRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class feedBackServices implements IfeedBackServices {
    private final IfeedBackRepository feedBackRepository;
    private final ItransactionRepository transactionRepository;


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

    @Override
    public List<bannedUser> getAllbanned() {
        int diffDays = 0;
        List<bannedUser> bannedlist= new ArrayList<bannedUser>();

        List<Transaction> alltransaction=( List<Transaction>)transactionRepository.findAll();
        for(Transaction t: alltransaction){


            for (Feedback f :  t.getFeedbacks()){
                long period = ChronoUnit.DAYS.between(f.getDateFeedback(), LocalDateTime.now());
                System.out.println(period);
                diffDays = (int) period;
                if(f.getCommentFeedback().contains("***") ){
                    if( diffDays >= 0 && diffDays <= 30){
                        bannedUser banned = new bannedUser(t.getUsers().getUsername(),t.getTransactionId(),f);
                        bannedlist.add(banned);

                    }


                }
            }

        }

        return bannedlist;
    }



 /*   public List<bannedUser> getAllbanned() {
        int diffDays = 0;

        List<Feedback> feedbackWithBadWord= new ArrayList<Feedback>();
        List<bannedUser> users = new ArrayList<bannedUser>();
        List<Feedback> feedback= (List<Feedback>)feedBackRepository.findAll();
         for (Feedback f : feedback){
             long period = ChronoUnit.DAYS.between(f.getDateFeedback(), LocalDateTime.now());
             System.out.println(period);
             diffDays = (int) period;
             if(f.getCommentFeedback().contains("***") ){
                 System.out.println("hiiii");
if( diffDays >= 0 && diffDays <= 30)
                 feedbackWithBadWord.add(f);
             }

         }

         if(feedbackWithBadWord.size()==0){
             return null;
         }
        for (Feedback feed : feedbackWithBadWord) {
            Transaction transaction = feed.getTransactions();
            if (transaction != null) {
                User user = transaction.getUsers();

                if (user != null) {
                    bannedUser banned =new bannedUser(user.getUsername(),feed.getTransactions());
                    users.add(banned);
                }
            }
        }


        return users;
    }

*/
}
