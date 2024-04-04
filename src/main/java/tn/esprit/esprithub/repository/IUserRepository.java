package tn.esprit.esprithub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.esprithub.entities.User;

public interface IUserRepository extends JpaRepository<User,Long> {

}
