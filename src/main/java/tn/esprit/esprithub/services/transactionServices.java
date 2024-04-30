package tn.esprit.esprithub.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.esprit.esprithub.DTO.statisticsTransaction;
import tn.esprit.esprithub.DTO.statisticsfeedbacks;
import tn.esprit.esprithub.DTO.transactionFeedback;
import tn.esprit.esprithub.entities.*;
import tn.esprit.esprithub.repository.IHousingRepository;
import tn.esprit.esprithub.repository.IarticleRepository;
import tn.esprit.esprithub.repository.IfeedBackRepository;
import tn.esprit.esprithub.repository.ItransactionRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        transaction.setPayementDateTransaction(LocalDateTime.now());
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
    public List<Transaction> getByIduser(Long iduser) {
        return (List<Transaction>) transactionRepository.findByUsersUserId(iduser);
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
    public statisticsfeedbacks statistics() {
        statisticsfeedbacks stat = new statisticsfeedbacks(0,0,0,0,0);
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

    @Override
    public statisticsTransaction statisticsTransaction() {
        List<Transaction>list=(List<Transaction>) transactionRepository.findAll();
        statisticsTransaction stat = new statisticsTransaction(0,0,0,0,0,0,0,0,0,0,0,0);


        for(int i= 0 ; i<list.size();i++){

            if(list.get(i).getPayementDateTransaction().getMonthValue() == 1){
                stat.setJanvier(stat.getJanvier()+1);
            }
            if(list.get(i).getPayementDateTransaction().getMonthValue() == 2){
                stat.setFevrier(stat.getFevrier()+1);
                if(list.get(i).getPayementDateTransaction().getMonthValue() == 3){
                    stat.setMars(stat.getMars()+1);
                }
            } if(list.get(i).getPayementDateTransaction().getMonthValue() == 4){
                stat.setAvril(stat.getAvril()+1);
            }
            if(list.get(i).getPayementDateTransaction().getMonthValue() == 5){
                stat.setMai(stat.getMai()+1);
            }
            if(list.get(i).getPayementDateTransaction().getMonthValue() == 6){
                stat.setJuin(stat.getJuin()+1);
            }
            if(list.get(i).getPayementDateTransaction().getMonthValue() == 7){
                stat.setJuillet(stat.getJuillet()+1);
            }
            if(list.get(i).getPayementDateTransaction().getMonthValue() == 8){
                stat.setAout(stat.getAout()+1);
            }
            if(list.get(i).getPayementDateTransaction().getMonthValue() == 9){
                stat.setSeptembre(stat.getSeptembre()+1);

            }
            if(list.get(i).getPayementDateTransaction().getMonthValue() == 10){
                stat.setOctobre(stat.getOctobre()+1);

            }
            if(list.get(i).getPayementDateTransaction().getMonthValue() == 11){
                stat.setNovembre(stat.getNovembre()+1);

            }

            if(list.get(i).getPayementDateTransaction().getMonthValue() == 12){
                stat.setDecembre(stat.getDecembre()+1);

            }


}
        return stat;
    }
    @Override
    public transactionFeedback getByIdIfBanned(Long id) {
        List<Transaction> transactions = getByIduser(id);

        if(transactions.size()==0){
             transactionFeedback transactionFeedback = new transactionFeedback(transactions, 0, 0);
            return transactionFeedback;
        }
        int diffDays = 0;



        boolean containsXXX = false;
        for (Transaction transaction : transactions) {
if(transaction.getFeedbacks().size()==0){
    transactionFeedback transactionFeedback = new transactionFeedback(transactions, 0, 0);

}
            for (Feedback feedback : transaction.getFeedbacks()) {
                if (feedback.getCommentFeedback().contains("***")) {
                    containsXXX = true;
                    long period = ChronoUnit.DAYS.between(feedback.getDateFeedback(), LocalDateTime.now());
                    System.out.println(period);
                    diffDays = (int) period;
                    break;
                }
            }
}
        if (!containsXXX) {
            transactionFeedback transactionFeedback = new transactionFeedback(transactions, 0, 0);
            return transactionFeedback;
        }

        if (containsXXX) {
            if (diffDays == 0) {
                transactionFeedback transactionFeedback = new transactionFeedback(transactions, 1, 30);
                return transactionFeedback;

            } else if (diffDays >= 30) {
                transactionFeedback transactionFeedback = new transactionFeedback(transactions, 0, 0);
                return transactionFeedback;

            } else if (diffDays > 0 && diffDays < 30) {
                transactionFeedback transactionFeedback = new transactionFeedback(transactions, 1, 30 - diffDays);
                return transactionFeedback;

            }
        }

        return null;
    }


    }
