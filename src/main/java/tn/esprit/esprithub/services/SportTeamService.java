package tn.esprit.esprithub.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tn.esprit.esprithub.entities.Field;
import tn.esprit.esprithub.entities.Reservation;
import tn.esprit.esprithub.entities.SportTeam;
import tn.esprit.esprithub.entities.User;
import tn.esprit.esprithub.repository.IFieldRepository;
import tn.esprit.esprithub.repository.IReservationRepository;
import tn.esprit.esprithub.repository.ISportTeamRepository;
import tn.esprit.esprithub.repository.IUserRepository;

import java.util.*;

@Service
@AllArgsConstructor
@Primary
public class SportTeamService implements ISportTeamService{
    private ISportTeamRepository sportTeamRepository;
    private IUserRepository userRepository;
    private IFieldRepository fieldRepository;
    private IReservationRepository reservationRepository;
    private EntityManager entityManager;

    @Override
    public SportTeam addSportTeam(SportTeam sportTeam) {
        return sportTeamRepository.save(sportTeam);
    }

    @Override
    public SportTeam updateSportTeam(SportTeam sportTeam) {
        return sportTeamRepository.save(sportTeam);
    }


    @Override
    public void deleteSportTeam(Long sportTeamId) {
        sportTeamRepository.deleteById(sportTeamId);
    }

    @Override
    public SportTeam getSportTeamById(Long sportTeamId) {
        return sportTeamRepository.findById(sportTeamId).orElse(null);
    }

    @Override
    public List<SportTeam> getAll() {
        return (List<SportTeam>) sportTeamRepository.findAll();
    }






    @Override
    public SportTeam addSportTeamCap(SportTeam sportTeam,Long captainId) {
        User user = userRepository.findById(captainId).orElse(null);

        sportTeam.setCaptain(user);

        user.setSportTeams(sportTeam);

        return sportTeamRepository.save(sportTeam);
    }

    @Override
    public SportTeam updateSportTeamCap(SportTeam sportTeam,Long sportTeamId) {
        SportTeam existingSportTeam=sportTeamRepository.findById(sportTeamId).orElse(null);
        if (existingSportTeam!= null){
            existingSportTeam.setNameTeam(sportTeam.getNameTeam());
            existingSportTeam.setLogoTeam(sportTeam.getLogoTeam());

        }
        return sportTeamRepository.save(existingSportTeam);
    }

//    @Override
//    public void deleteSportTeamCap(Long sportTeamId) {
//        SportTeam sportTeam = sportTeamRepository.findById(sportTeamId).orElse(null);
//
//        if (sportTeam != null) {
//            Long captainId = 5L;
//
//            if (sportTeam.getCaptain().getUserId().equals(captainId)) {
//                sportTeamRepository.deleteById(sportTeamId);
//            } else {
//                throw new EntityNotFoundException("Only the captain can delete the sport team.");
//            }
//        } else {
//            throw new EntityNotFoundException("Sport team not found with ID: " + sportTeamId);
//        }
//    }

    @Override
    public void deleteSportTeamCap(Long sportTeamId, Long captainId) {
        SportTeam sportTeam = sportTeamRepository.findById(sportTeamId).orElse(null);

        if (sportTeam != null) {
            if (sportTeam.getCaptain().getUserId().equals(captainId)) {
                // Dissociate users from the sport team
                for (User user : sportTeam.getUsers()) {
                    user.setSportTeams(null);
                }
                sportTeam.setCaptain(null);
                sportTeam.getUsers().clear();


                sportTeamRepository.deleteById(sportTeamId);
            } else {
                throw new IllegalArgumentException("Only the captain can delete the sport team and the provided field must exist.");
            }
        } else {
            throw new EntityNotFoundException("Sport team not found with ID: " + sportTeamId);
        }
    }









    @Override
    public void addUserToSportTeam(Long sportTeamId, Long userId) {
        SportTeam sportTeam = sportTeamRepository.findById(sportTeamId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (sportTeam != null && user != null) {
            sportTeam.getUsers().add(user);
            user.setSportTeams(sportTeam);
            sportTeamRepository.save(sportTeam);

        }
    }


    @Override
    public void removeUserFromSportTeam(Long sportTeamId, Long userId) {
        SportTeam sportTeam = sportTeamRepository.findById(sportTeamId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (sportTeam != null && user != null) {
            sportTeam.getUsers().remove(user);
            user.setSportTeams(null);
            sportTeamRepository.save(sportTeam);
        }
    }





    @Override
    public void participateSportTeam(Long sportTeamId, Long userId) {
        SportTeam sportTeam = sportTeamRepository.findById(sportTeamId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (sportTeam != null && user != null) {
            sportTeam.getUsers().add(user);
            user.setSportTeams(sportTeam);
            sportTeamRepository.save(sportTeam);

        }
    }

    @Override
    public void cancelParticipation(Long sportTeamId,Long userId) {
        SportTeam sportTeam = sportTeamRepository.findById(sportTeamId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (sportTeam != null && user != null) {
            sportTeam.getUsers().remove(user);
            user.setSportTeams(null);
            sportTeamRepository.save(sportTeam);
        }
    }





    @Override
    public List<User> getUsersBySportTeamId(Long sportTeamId) {
        return sportTeamRepository.findUserIdsBySportTeamId(sportTeamId);
    }
//    @Override
//    public void makeTeamReservation(Long sportTeamId, Long captainId, Long fieldId, Reservation reservation) {
//        SportTeam sportTeam = sportTeamRepository.findById(sportTeamId).orElse(null);
//        Field field = fieldRepository.findById(fieldId).orElse(null);
//
//        if (sportTeam != null && sportTeam.getCaptain().getUserId().equals(captainId) && field != null) {
//            Set<User> teamMembers = sportTeam.getUsers();
//
//            for (User member : teamMembers) {
//                reservation.setUsers(new HashSet<>(Collections.singletonList(member)));
//                reservation.setFields(field);
//                member.getReservations().add(reservation);
//            }
//
//            userRepository.saveAll(teamMembers);
//        } else {
//            throw new IllegalArgumentException("Only the captain can make a team reservation and the provided field must exist.");
//        }
//    }


//    @Override
//    public void makeTeamReservation(Long sportTeamId, Long captainId, Long fieldId, Reservation reservation) {
//        SportTeam sportTeam = sportTeamRepository.findById(sportTeamId).orElse(null);
//        Field field = fieldRepository.findById(fieldId).orElse(null);
//
//        if (sportTeam != null && sportTeam.getCaptain().getUserId().equals(captainId) && field != null) {
//            Set<User> teamMembers = sportTeam.getUsers();
//
//            Reservation teamReservation = new Reservation();
//            teamReservation.setStartDate(reservation.getStartDate());
//            teamReservation.setEndDate(reservation.getEndDate());
//            teamReservation.setNbPlayers(reservation.getNbPlayers());
//            teamReservation.setResStatus(reservation.getResStatus());
//            teamReservation.setResType(reservation.getResType());
//            teamReservation.setFields(field);
//
//            for (User member : teamMembers) {
//                member.getReservations().add(teamReservation);
//                reservationRepository.save(teamReservation);
//            }
//
//            userRepository.saveAll(teamMembers);
//        } else {
//            throw new IllegalArgumentException("Only the captain can make a team reservation and the provided field must exist.");
//        }
//    }
//
@Override
public void makeTeamReservation(Long sportTeamId, Long captainId, Long fieldId, Reservation reservation) {
    SportTeam sportTeam = sportTeamRepository.findById(sportTeamId).orElse(null);
    Field field = fieldRepository.findById(fieldId).orElse(null);

    if (sportTeam != null && sportTeam.getCaptain().getUserId().equals(captainId) && field != null) {
        Reservation teamReservation = new Reservation();
        teamReservation.setStartDate(reservation.getStartDate());
        teamReservation.setEndDate(reservation.getEndDate());
        teamReservation.setNbPlayers(reservation.getNbPlayers());
        teamReservation.setResStatus(reservation.getResStatus());
        teamReservation.setResType(reservation.getResType());
        teamReservation.setFields(field);

        Set<User> teamMembers = sportTeam.getUsers();

        for (User member : teamMembers) {
            member.getReservations().add(teamReservation);
        }

        reservationRepository.save(teamReservation);
        userRepository.saveAll(teamMembers);
    } else {
        throw new IllegalArgumentException("Only the captain can make a team reservation and the provided field must exist.");
    }
}




}
