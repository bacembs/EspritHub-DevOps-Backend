package tn.esprit.esprithub.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long reservationId;
    LocalDateTime startDate;
    LocalDateTime endDate;
    Long nbPlayers;
    @Enumerated(EnumType.STRING)
    Rstatus resStatus;
    @Enumerated(EnumType.STRING)
    TypeR  resType;

    @ManyToMany(mappedBy="reservations")
    Set<User> users ;

    @ManyToOne
    Field fields;


}
