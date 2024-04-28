package tn.esprit.esprithub.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.esprit.esprithub.DTO.statistics;
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

        Transaction savedTransaction = transactionRepository.save(transaction);
if(savedTransaction.getFeedbacks() !=null) {
    for (Feedback feedback : savedTransaction.getFeedbacks()) {
        feedback.setTransactions(savedTransaction);
        feedBackRepository.save(feedback);
    }
}

       if(!savedTransaction.getArticles().isEmpty()) {
            for (Article article : savedTransaction.getArticles()) {

                article.setTransactions(savedTransaction);

                articleRepository.save(article);

            }
        }




        if(savedTransaction.getHousing()!=null) {
            System.out.println("debut");
            savedTransaction.getHousing().setTransaction(savedTransaction);
            housingRepository.save(savedTransaction.getHousing());
            System.out.println(savedTransaction.getHousing());
            System.out.println("fin");

        }

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
    public ResponseEntity<Transaction> affection(Transaction transaction) {
        User user = userService.getByNom(transaction.getUsers().getUsername());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null); // Ou un message d'erreur approprié dans le corps de la réponse
        }


        transaction.setUsers(user);

        return ResponseEntity.ok(transaction);
    }

    @Override
    public statistics statistics() {
        statistics stat = new statistics(0,0,0,0,0);
        List <Feedback> a= (List<Feedback>) feedBackRepository.findAll();
        for(int i = 0; i <a.size(); i++){
            if(a.get(i).getGradeFeedback()==1){
                stat.setNote1(stat.getNote1()+1);

            }

            if(a.get(i).getGradeFeedback()==2){
                stat.setNote2(stat.getNote2()+1);

            }

            if(a.get(i).getGradeFeedback()==3){
                stat.setNote3(stat.getNote3()+1);

            }
            if(a.get(i).getGradeFeedback()==4){
                stat.setNote4(stat.getNote4()+1);

            }

            if(a.get(i).getGradeFeedback()==5){
                stat.setNote5(stat.getNote5()+1);

            }
        }
        return stat;
    }


}
