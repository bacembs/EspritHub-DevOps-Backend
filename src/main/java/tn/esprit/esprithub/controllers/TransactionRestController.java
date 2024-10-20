package tn.esprit.esprithub.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.esprithub.DTO.statisticsTransaction;
import tn.esprit.esprithub.DTO.statisticsfeedbacks;
import tn.esprit.esprithub.DTO.transactionFeedback;
import tn.esprit.esprithub.entities.Feedback;
import tn.esprit.esprithub.entities.Transaction;
import tn.esprit.esprithub.services.ItransactionServices;
import java.util.List;

@RestController
//@CrossOrigin(origins = "*")

@AllArgsConstructor
@RequestMapping("/Transaction")
public class TransactionRestController {
    private ItransactionServices serviceTransaction ;
    @PostMapping("/add")
    public Transaction addTransaction(@RequestBody Transaction transaction){
        return serviceTransaction.addTransaction(transaction);
    }

    @PostMapping("/add2")
    public Transaction add2Transaction(@RequestBody Transaction transaction) {

        return serviceTransaction.addTransaction(transaction);
    }
    @PostMapping("/add/feedback/{id}")
    public boolean addfeedbacktoTransaction(@PathVariable Long id ,@RequestBody Feedback f){
        return serviceTransaction.addFeedbackFromTransactionId(id, f);
    }
    @GetMapping("/all")

    public List<Transaction> getAll() {
        List<Transaction> transactions = serviceTransaction.getAll();

        if (transactions.isEmpty()) {
            // Aucune transaction trouvée, renvoyer une réponse avec le statut NOT_FOUND
            return null; // Ou un message d'erreur approprié dans le corps de la réponse
        }

        // Des transactions sont trouvées, renvoyer une réponse OK avec la liste de transactions
        return transactions;
    }




    @GetMapping("/transactionbyIdUser/{id}")
    public List<Transaction> getTransactionbyIdUser(@PathVariable Long id) {
        return serviceTransaction.getByIduser(id);
    }


    @GetMapping("/byId/{id}")
    public transactionFeedback getByIdIfBanned(@PathVariable Long id) {
        return serviceTransaction.getByIdIfBanned(id);
    }
/*
    public transactionFeedback getByIdIfBnned(@PathVariable Long id) {
        List<Transaction> transactions = serviceTransaction.getByIduser(id);
        int diffDays =0;
        if (transactions.isEmpty()) {
            return null;
        }

        boolean containsXXX = false;
        for (Transaction transaction : transactions) {
            for (Feedback feedback : transaction.getFeedbacks()) {
                if (feedback.getCommentFeedback().contains("***")) {
                    containsXXX = true;
                    long  period = ChronoUnit.DAYS.between(feedback.getDateFeedback(),LocalDateTime.now());
                    System.out.println(period);
diffDays=(int)period;
                    break;
                }
            }

        }
if(!containsXXX){
            transactionFeedback transactionFeedback = new transactionFeedback(transactions, 0,0);
    return transactionFeedback;
        }

        if(containsXXX){
            if(diffDays==0){
                transactionFeedback transactionFeedback = new transactionFeedback(transactions,  1  ,30);
                return transactionFeedback;

            }

            else if(diffDays>=30){
                transactionFeedback transactionFeedback = new transactionFeedback(transactions, 0 ,0);
                return transactionFeedback;

            }
            else  if (diffDays > 0 && diffDays < 30){
                transactionFeedback transactionFeedback = new transactionFeedback(transactions, 1, 30-diffDays);
                return transactionFeedback;

            }
        }
        return null;
    }
*/
    @GetMapping("/statistique")
    public statisticsfeedbacks stat() {

        return serviceTransaction.statistics();

    }

    @GetMapping("/statistiquetransaction")
    public statisticsTransaction statistiquetransaction() {

        return serviceTransaction.statisticsTransaction();

    }



}

