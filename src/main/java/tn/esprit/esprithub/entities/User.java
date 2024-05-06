package tn.esprit.esprithub.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
//UserDetails: standard interface used by spring security
//Principal: indicates that this class is the "ID" for the current logged in user
public class User implements UserDetails, Principal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;
    String username;
    String firstName;
    String lastName;
    @Column(unique = true)
    String email;
    String password;
    String adresse;
    int phone;
    String imgUser;
    @Enumerated(EnumType.STRING)
    Roles role;
    String lastLogin;
    boolean accountLocked;
    boolean enabled;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

    @JsonIgnore
    @OneToMany(mappedBy = "users")


    Set<Article> articles;

    @Enumerated(EnumType.STRING)
    Badge badge = Badge.NOBADGE;
    boolean participationTeam =false;


    @OneToMany(mappedBy = "users")
    @JsonIgnore

    Set<Transaction> transactions;

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
    @JsonBackReference
    @OneToMany(mappedBy = "owner")
    private List<Housing> ownedHousing;

    @ManyToMany(mappedBy = "renters")
    private List<Housing> rentedHousing;


    @Override
    //5tarna email houa identifier mta3 user
    public String getName() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Assuming your Roles enum is named Roles and has lowercase role names
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name().toUpperCase()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String fullname() {
        return firstName + " " + lastName;
    }
}
