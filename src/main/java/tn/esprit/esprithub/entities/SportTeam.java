package tn.esprit.esprithub.entities;

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
public class SportTeam implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long teamId;
    String nameTeam;
    int captainId;
    String logoTeam;

    @OneToMany(mappedBy = "sportTeams")
    Set<User> users;
}
