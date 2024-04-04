package tn.esprit.esprithub.services;

import tn.esprit.esprithub.entities.SportTeam;
import tn.esprit.esprithub.entities.User;

import java.util.List;

public interface ISportTeamService {
    SportTeam addSportTeam(SportTeam sportTeam);
    SportTeam updateSportTeam(SportTeam sportTeam);
    void deleteSportTeam(Long sportTeamId);
    SportTeam getSportTeamById(Long sportTeamId);
    List<SportTeam> getAll();
    public void addUserToSportTeam(Long sportTeamId, Long userId);

    SportTeam addSportTeamCap(SportTeam sportTeam, Long captainId);

    SportTeam updateSportTeamCap(SportTeam sportTeam,Long sportTeamId);
    void deleteSportTeamCap(Long sportTeamId);
    void participateSportTeam(Long sportTeamId);
    void cancelParticipation(Long sportTeamId);
    void removeUserFromSportTeam(Long sportTeamId, Long userId);

    List<User> getUsersBySportTeamId(Long sportTeamId);

    }
