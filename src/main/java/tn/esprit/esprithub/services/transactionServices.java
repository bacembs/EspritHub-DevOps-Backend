package tn.esprit.esprithub.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.esprit.esprithub.entities.*;
import tn.esprit.esprithub.repository.IHousingRepository;
import tn.esprit.esprithub.repository.IarticleRepository;
import tn.esprit.esprithub.repository.IfeedBackRepository;
import tn.esprit.esprithub.repository.ItransactionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class transactionServices  implements ItransactionServices{
    private final ItransactionRepository transactionRepository;
    private final IfeedBackRepository feedBackRepository;
    private final IHousingRepository housingRepository;
    private final IarticleRepository articleRepository;


    private final UserServices userService;
    private final ArticleServices articleservice;

    @Override
    public Transaction addTransaction(Transaction transaction) {

        userService.getByNom(transaction.getUsers().getUsername());
        Transaction savedTransaction = transactionRepository.save(transaction);
        System.out.println(savedTransaction.toString());
if(savedTransaction.getFeedbacks() !=null) {
    for (Feedback feedback : savedTransaction.getFeedbacks()) {
        feedback.setTransactions(savedTransaction);
        feedBackRepository.save(feedback);
    }
}

        if(savedTransaction.getArticles().size()>0) {
            System.out.println("aaloo");
            for (Article article : savedTransaction.getArticles()) {

                article.setTransactions(savedTransaction);
                System.out.println("aalooallo");

                articleRepository.save(article);

            }
        }





        System.out.println("debut");
        savedTransaction.getHousing().setTransaction(savedTransaction);
        housingRepository.save(savedTransaction.getHousing());
        System.out.println(savedTransaction.getHousing());
        System.out.println("fin");



        return transaction;
        }

    @Override
    public Transaction updateTransaction(Transaction transaction) {
       return transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransaction(Long Transaction) {

    }

    @Override
    public Transaction getById(Long numTransaction) {
        return null;
    }

    @Override
    public List<Transaction> getAll() {
        return (List<Transaction>) transactionRepository.findAll();
    }

    @Override

    public boolean addFeedbackFromTransactionId(Long transactionId, Feedback feedback) {
        Transaction transactionToUpdate = transactionRepository.findById(transactionId).orElse(null);
        feedback.setDateFeedback(LocalDateTime.now());


            // Associer le feedback à la transaction
            feedback.setTransactions(transactionToUpdate);
        feedBackRepository.save(feedback);

            return true;
        }



    @Override
    public ResponseEntity<Transaction> affection(TransactionRequest transaction) {
        User user = userService.getByNom(transaction.getUsername());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null); // Ou un message d'erreur approprié dans le corps de la réponse
        }

        Transaction t = new Transaction(transaction.getTransactionId(), transaction.getAmountTransaction(),
                LocalDateTime.now(), transaction.getFeedbacks(), transaction.getArticles(),
                transaction.getHousing(),null);
        t.setUsers(user);

        return ResponseEntity.ok(t);
    }




}
