package tn.esprit.esprithub.DTO;


import tn.esprit.esprithub.entities.Article;
import tn.esprit.esprithub.entities.Feedback;
import tn.esprit.esprithub.entities.Housing;
import tn.esprit.esprithub.entities.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class transactionFeedback {
    List<Transaction> transactions;
    int banned ;

    int days ;

    public transactionFeedback(List<Transaction> transactions, int banned, int days) {
        this.transactions = transactions;
        this.banned = banned;
        this.days = days;
    }

    public transactionFeedback(List<Transaction> transactions, int banned) {
        this.transactions = transactions;
        this.banned = banned;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public int getBanned() {
        return banned;
    }

    public void setBanned(int banned) {
        this.banned = banned;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
