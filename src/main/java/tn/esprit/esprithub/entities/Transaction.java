package tn.esprit.esprithub.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long transactionId;
    Float amountTransaction;
    LocalDateTime payementDateTransaction;

    @OneToMany(mappedBy = "transactions")
    Set<Feedback> feedbacks;

    @OneToMany(mappedBy = "transactions")
    Set<Article> articles;
    @OneToOne(mappedBy = "transaction")
    Housing housing;

    @ManyToOne
    User users;


    public Transaction(Long transactionId,Float amountTransaction, LocalDateTime
            payementDateTransaction, Set<Feedback> feedbacks, Set<Article> articles, User users) {
        this.transactionId = transactionId;

        this.amountTransaction = amountTransaction;
        this.payementDateTransaction = payementDateTransaction;
        this.feedbacks = feedbacks;
        this.articles = articles;
        this.users = users;
    }

    public Transaction(Long transactionId, Float amountTransaction, LocalDateTime payementDateTransaction,
                       Set<Feedback> feedbacks, Housing housing, User users) {
        this.transactionId = transactionId;
        this.amountTransaction = amountTransaction;
        this.payementDateTransaction = payementDateTransaction;
        this.feedbacks = feedbacks;
        this.housing = housing;
        this.users = users;
    }
}
