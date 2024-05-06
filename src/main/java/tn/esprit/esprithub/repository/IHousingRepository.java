package tn.esprit.esprithub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.esprithub.entities.Housing;

import java.util.List;

public interface IHousingRepository extends JpaRepository<Housing, Long> {
    List<Housing> findHousingByOwner_UserId(Long ownerId);
}
