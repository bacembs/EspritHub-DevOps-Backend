package tn.esprit.esprithub.repository;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.esprithub.entities.Transaction;
import tn.esprit.esprithub.entities.User;

import java.util.Optional;

public interface IuserRepository extends CrudRepository<User, Long> {
    User findByUsername(String nom);

}
