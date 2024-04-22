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
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;
    String username;
    String firstName;
    String lastName;
    String email;
    String password;
    int phone;
    @Enumerated(EnumType.STRING)
    Roles role;
    String imgUser;
    String lastLogin;


    @OneToMany(mappedBy = "users")
    Set<Article> articles;

    @OneToMany(mappedBy = "users")
    Set<Complaint> complaints;

    @ManyToOne
    Internship internships;


    @OneToMany(mappedBy = "users")
    Set<Participants> participants;

    @ManyToOne
    SportTeam sportTeams;

    @ManyToMany
    @JsonIgnore
    Set<Reservation>reservations;

    @OneToMany(mappedBy = "users")
    Set<FreelanceJob> jobs;

}
