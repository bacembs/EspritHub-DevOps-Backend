package tn.esprit.projetpi.services;

import tn.esprit.projetpi.entities.SportTeam;

import java.util.List;

public interface ISportTeamService {
    SportTeam addSportTeam(SportTeam sportTeam);
    SportTeam updateSportTeam(SportTeam sportTeam);
    void deleteSportTeam(Long sportTeamId);
    SportTeam getSportTeamById(Long sportTeamId);
    List<SportTeam> getAll();
}
