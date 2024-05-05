package tn.esprit.esprithub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.esprithub.entities.User;

import javax.management.relation.Role;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findByRole(Role role);
}
