package tn.esprit.esprithub.services;

import org.springframework.http.ResponseEntity;
import tn.esprit.esprithub.DTO.statisticsTransaction;
import tn.esprit.esprithub.DTO.statisticsfeedbacks;
import tn.esprit.esprithub.entities.Feedback;
import tn.esprit.esprithub.entities.Transaction;

import java.util.List;

public interface ItransactionServices {
    Transaction  addTransaction(Transaction transaction);
    Transaction updateTransaction(Transaction transaction);
    void deleteTransaction(Long Transaction);
    List<Transaction>  getByIduser(Long iduser);
    List<Transaction> getAll();
    public boolean addFeedbackFromTransactionId(Long transactionId, Feedback feedback) ;
    public ResponseEntity<Transaction> affection(Transaction transaction);
    public statisticsfeedbacks statistics() ;
    public statisticsTransaction statisticsTransaction() ;

    }
