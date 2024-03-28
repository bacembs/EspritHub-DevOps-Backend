package tn.esprit.esprithub.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Article implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long articleId;
    @Enumerated(EnumType.STRING)
    Mycategory category;
    @Enumerated(EnumType.STRING)
    Mycondition condition;
    String imgArticle;
    String descriptionArticle;
    Float priceArticle;

    @ManyToOne
    User users;

    @ManyToOne
    Transaction transactions;
}
