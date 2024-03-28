package tn.esprit.projetpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetpi.entities.SportTeam;

public interface ISportTeamRepository extends JpaRepository<SportTeam,Long> {
}
