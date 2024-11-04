package tn.esprit.esprithub.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long reservationId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    LocalDateTime endDate;

    Long nbPlayers =1L;
    @Enumerated(EnumType.STRING)
    Rstatus resStatus = Rstatus.pending;
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

    public void updateStatus() {
        if (nbPlayers >= fields.getCapacityField()) {
            this.resStatus = Rstatus.confirmed;
        } else {
            this.resStatus = Rstatus.pending;
        }
    }



}
