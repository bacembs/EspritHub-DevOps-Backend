package tn.esprit.esprithub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.esprithub.entities.SportTeam;
import tn.esprit.esprithub.entities.User;

import java.util.List;

public interface ISportTeamRepository extends JpaRepository<SportTeam,Long> {
    @Query("SELECT u FROM User u JOIN u.sportTeams st WHERE st.teamId = :sportTeamId")
  //  List<Long> findUserIdsBySportTeamId(Long sportTeamId);
    List<User> findUserIdsBySportTeamId(@Param("sportTeamId") Long sportTeamId);


}
