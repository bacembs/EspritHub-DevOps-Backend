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
public class SportTeam implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long teamId;
    String nameTeam;
    String logoTeam;

    @OneToMany(mappedBy = "sportTeams", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<User> users;

    @OneToOne
    @JsonIgnore
    User captain;



}
