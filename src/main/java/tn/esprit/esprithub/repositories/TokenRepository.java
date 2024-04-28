package tn.esprit.esprithub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.esprithub.entities.Token;

import java.util.Optional;

public interface TokenRepository extends JpaRepository <Token, Long> {
    Optional<Token> findByToken(String token);
}
