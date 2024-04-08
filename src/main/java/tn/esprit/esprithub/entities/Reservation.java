package tn.esprit.esprithub.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
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

    @ManyToMany(mappedBy = "reservations")
    @JsonIgnore
    private Set<User> users = new HashSet<>();
//    @ManyToMany(mappedBy="reservations")
//    @JsonIgnore
//    Set<User> users = new HashSet<>();

    @ManyToOne
    @JsonIgnore
    Field fields;



}
