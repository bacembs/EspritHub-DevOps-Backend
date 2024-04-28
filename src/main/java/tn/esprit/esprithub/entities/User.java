package tn.esprit.esprithub.entities;

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
