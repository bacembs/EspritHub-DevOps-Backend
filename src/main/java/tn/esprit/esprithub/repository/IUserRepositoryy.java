package tn.esprit.esprithub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.esprithub.entities.User;

import java.util.List;

public interface IUserRepositoryy extends JpaRepository<User,Long> {
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.reservations r WHERE (u.badge = 'NOBADGE' OR u.badge = 'SILVER' OR u.badge = 'GOLD') AND SIZE(u.reservations) >= 1")
    List<User> findUsersWithUpdatedBadges();

    User findByEmail(String email);


}
