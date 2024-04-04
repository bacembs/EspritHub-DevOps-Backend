package tn.esprit.esprithub.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tn.esprit.esprithub.entities.SportTeam;
import tn.esprit.esprithub.entities.User;
import tn.esprit.esprithub.repository.ISportTeamRepository;
import tn.esprit.esprithub.repository.IUserRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Primary
public class SportTeamService implements ISportTeamService{
    private ISportTeamRepository sportTeamRepository;
    private IUserRepository userRepository;
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

    @Override
    public void deleteSportTeamCap(Long sportTeamId) {
        SportTeam sportTeam = sportTeamRepository.findById(sportTeamId).orElse(null);

        if (sportTeam != null) {
            Long captainId = 1L;

            if (sportTeam.getCaptain().getUserId().equals(captainId)) {
                sportTeamRepository.deleteById(sportTeamId);
            } else {
                throw new EntityNotFoundException("Only the captain can delete the sport team.");
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
    public void participateSportTeam(Long sportTeamId) {
        SportTeam sportTeam = sportTeamRepository.findById(sportTeamId).orElse(null);
        User user = userRepository.findById(1L).orElse(null);

        if (sportTeam != null && user != null) {
            sportTeam.getUsers().add(user);
            user.setSportTeams(sportTeam);
            sportTeamRepository.save(sportTeam);

        }
    }

    @Override
    public void cancelParticipation(Long sportTeamId) {
        SportTeam sportTeam = sportTeamRepository.findById(sportTeamId).orElse(null);
        User user = userRepository.findById(1L).orElse(null);

        if (sportTeam != null && user != null) {
            sportTeam.getUsers().remove(user); // Remove the user from the sport team
            user.setSportTeams(null); // Set the user's sport team to null
            sportTeamRepository.save(sportTeam); // Save the changes to the sport team
        }
    }





    @Override
    public List<User> getUsersBySportTeamId(Long sportTeamId) {
        return sportTeamRepository.findUserIdsBySportTeamId(sportTeamId);
    }





}
