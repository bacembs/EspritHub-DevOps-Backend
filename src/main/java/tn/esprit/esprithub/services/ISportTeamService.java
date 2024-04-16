package tn.esprit.esprithub.services;

import tn.esprit.esprithub.entities.Reservation;
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
   // void deleteSportTeamCap(Long sportTeamId);
    void deleteSportTeamCap(Long sportTeamId, Long captainId);
    void participateSportTeam(Long sportTeamId, Long userId);
    void cancelParticipation(Long sportTeamId,Long userId);
    void removeUserFromSportTeam(Long sportTeamId, Long userId);

    List<User> getUsersBySportTeamId(Long sportTeamId);
  //  void makeTeamReservation(Long sportTeamId, Long captainId, Reservation reservation);

   // void makeTeamReservation(Long sportTeamId, Reservation reservation);

    public void makeTeamReservation(Long sportTeamId, Long captainId, Long fieldId, Reservation reservation);

}
