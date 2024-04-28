package tn.esprit.esprithub.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.esprithub.DTO.statistics;
import tn.esprit.esprithub.entities.Feedback;
import tn.esprit.esprithub.entities.Transaction;
import tn.esprit.esprithub.entities.TransactionRequest;
import tn.esprit.esprithub.services.IfeedBackServices;
import tn.esprit.esprithub.services.ItransactionServices;
import tn.esprit.esprithub.services.UserServices;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@AllArgsConstructor
@RequestMapping("/Transaction")
public class TransactionRestController {
    private ItransactionServices serviceTransaction ;
    private UserServices serviceuser ;

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



    @GetMapping("/statistique")
    public  statistics stat() {

        return serviceTransaction.statistics();

    }



}

