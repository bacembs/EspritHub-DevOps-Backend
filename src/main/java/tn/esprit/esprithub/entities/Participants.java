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
public class Participants implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long participantId;

    @ManyToOne
    User users;

    @ManyToOne
    Event events;

}
