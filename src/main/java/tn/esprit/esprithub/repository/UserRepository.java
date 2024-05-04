package tn.esprit.esprithub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.esprithub.entities.User;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
