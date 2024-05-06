package tn.esprit.esprithub.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Visit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long visiteID;



     String description;
     LocalDateTime startDateTime; // Date et heure de début de la visite
     LocalDateTime endDateTime;

    @ManyToOne
    @JsonIgnore
    User visiteur;

    @ManyToOne
    Housing housing;
}

