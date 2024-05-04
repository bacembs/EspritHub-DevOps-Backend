package tn.esprit.esprithub.entities;

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

    /*@OneToOne
    @JoinColumn(name = "basket_id")
    Basket basket;*/
}
