package tn.esprit.esprithub.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.HashSet;
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

 //    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    @JoinColumn(name = "team_id")
    @ManyToOne
    @JsonIgnore
    SportTeam sportTeams;

    @OneToOne(mappedBy="captain")
    @JsonIgnore
    SportTeam sportTeamCaptain;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_reservation",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "reservation_id"))
    private Set<Reservation> reservations = new HashSet<>();
//    @ManyToMany
//    @JsonIgnore
//    Set<Reservation>reservations= new HashSet<>();

    @OneToMany(mappedBy = "users")
    Set<FreelanceJob> jobs;

}
