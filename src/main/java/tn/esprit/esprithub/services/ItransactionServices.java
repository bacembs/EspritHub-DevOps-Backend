package tn.esprit.esprithub.services;

import org.springframework.http.ResponseEntity;
import tn.esprit.esprithub.entities.Feedback;
import tn.esprit.esprithub.entities.Transaction;
import tn.esprit.esprithub.entities.TransactionRequest;

import java.util.List;

public interface ItransactionServices {
    Transaction  addTransaction(Transaction transaction);
    Transaction updateTransaction(Transaction transaction);
    void deleteTransaction(Long Transaction);
    Transaction getById(Long numTransaction);
    List<Transaction> getAll();
    public boolean addFeedbackFromTransactionId(Long transactionId, Feedback feedback) ;
    public ResponseEntity<Transaction> affection(TransactionRequest transaction);

    }
