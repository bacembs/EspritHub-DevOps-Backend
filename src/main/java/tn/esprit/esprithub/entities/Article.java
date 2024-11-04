package tn.esprit.esprithub.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Set;

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
    String nameArticle;
    @Enumerated(EnumType.STRING)
    Mycategory categoryArticle;
    @Enumerated(EnumType.STRING)
    Mycondition conditionArticle;
    String imgArticle;
    String descriptionArticle;
    Float priceArticle;


    @ManyToOne
    User users;

    @ManyToOne
    @JsonIgnore

    Transaction transactions;

}
