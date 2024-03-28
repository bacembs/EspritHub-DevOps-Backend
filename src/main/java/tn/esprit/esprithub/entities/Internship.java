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
public class Internship implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long internshipId;
    String titleInternship;
    String companyInternship;
    String durationInternship;
    String descriptionInternship;
    String locationInternship;
    String skillsRequiredInternship;
    String responsibilitiesInternship;
    LocalDateTime deadlineInternship;

    @OneToMany(mappedBy = "internships")
    Set<User> users;
}
