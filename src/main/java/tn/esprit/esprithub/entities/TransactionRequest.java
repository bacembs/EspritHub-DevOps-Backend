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
public class TransactionRequest {



        Long transactionId;
        Float amountTransaction;
        LocalDateTime payementDateTransaction;

        Set<Feedback> feedbacks;

        Set<Article> articles;
Housing housing ;
       String username;
    }


