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
public class FreelanceJob implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long jobId;
    String titleJob;
    String clientJob;
    String durationJob;
    String locationJob;
    String skillsRequiredJob;
    String descriptionJob;
    Float budgetJob;
    LocalDateTime deadlineJob;

    @ManyToOne
    User users;

    @OneToOne(cascade= CascadeType.PERSIST)
    Transaction transaction;
}
