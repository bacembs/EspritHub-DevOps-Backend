package tn.esprit.esprithub.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.esprithub.entities.Field;
import tn.esprit.esprithub.entities.Reservation;
import tn.esprit.esprithub.entities.SportTeam;
import tn.esprit.esprithub.entities.User;
import tn.esprit.esprithub.repository.IFieldRepository;
import tn.esprit.esprithub.repository.IReservationRepository;
import tn.esprit.esprithub.repository.ISportTeamRepository;
import tn.esprit.esprithub.repository.IUserRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        SportTeam sportTeam = sportTeamRepository.findById(sportTeamId).orElse(null);

        if (sportTeam != null) {
            for (User user : sportTeam.getUsers()) {
                user.setSportTeams(null);
                user.setParticipationTeam(false);
            }
            sportTeam.setCaptain(null);
            sportTeam.getUsers().clear();


            sportTeamRepository.deleteById(sportTeamId);
        }
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
    public SportTeam addSportTeamCap2(SportTeam sportTeam, Long captainId, MultipartFile photoFile) {
        User user = userRepository.findById(captainId).orElse(null);
        String uploadPath = "C:\\Users\\Bacem\\IdeaProjects\\EspritHub\\src\\main\\resources\\static\\photos\\";
        try {
            byte[] bytes = photoFile.getBytes();
            String fileName = photoFile.getOriginalFilename();
            Path path = Paths.get(uploadPath + fileName);
            Files.write(path, bytes);
            sportTeam.setLogoTeam(fileName);
            sportTeam.setCaptain(user);

            user.setSportTeams(sportTeam);
            user.setParticipationTeam(true);
            sportTeamRepository.save(sportTeam);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return sportTeamRepository.save(sportTeam);
    }
@Override
@Transactional
public SportTeam addSportTeamCap3(String teamName, Long captainId, MultipartFile photoFile) {
    SportTeam sportTeam = new SportTeam();

    User user = userRepository.findById(captainId).orElseThrow(() -> new EntityNotFoundException("User not found"));

    String uploadPath = "C:\\Users\\Bacem\\IdeaProjects\\EspritHub\\src\\main\\resources\\static\\photos\\";
    try {
        byte[] bytes = photoFile.getBytes();
        String fileName = StringUtils.cleanPath(photoFile.getOriginalFilename());
        Path path = Paths.get(uploadPath, fileName);

        Files.createDirectories(path.getParent());

        try (OutputStream os = Files.newOutputStream(path)) {
            os.write(bytes);
        }
        sportTeam.setNameTeam(teamName);
        sportTeam.setLogoTeam(fileName);
        sportTeam.setCaptain(user);
        user.setSportTeams(sportTeam);
        user.setParticipationTeam(true);
        userRepository.save(user);
        sportTeamRepository.save(sportTeam);

    } catch (IOException e) {


    }
    return sportTeam;
}

    @Override
    public SportTeam updateSportTeamCapWithPhoto(String teamName, Long sportTeamId, MultipartFile photoFile) {
        SportTeam existingSportTeam = sportTeamRepository.findById(sportTeamId).orElse(null);
        if (existingSportTeam != null) {
            existingSportTeam.setNameTeam(teamName);
            if (photoFile != null && !photoFile.isEmpty()) {
                try {
                    String uploadPath = "C:\\Users\\Bacem\\IdeaProjects\\EspritHub\\src\\main\\resources\\static\\photos\\";
                    byte[] bytes = photoFile.getBytes();
                    String fileName = StringUtils.cleanPath(photoFile.getOriginalFilename());
                    Path path = Paths.get(uploadPath, fileName);
                    Files.createDirectories(path.getParent());
                    try (OutputStream os = Files.newOutputStream(path)) {
                        os.write(bytes);
                    }
                    existingSportTeam.setLogoTeam(fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sportTeamRepository.save(existingSportTeam);
        }
        return null;
    }




    @Override
    public SportTeam addSportTeamCap(SportTeam sportTeam,Long captainId) {
        User user = userRepository.findById(captainId).orElse(null);

        sportTeam.setCaptain(user);

        user.setSportTeams(sportTeam);
        user.setParticipationTeam(true);

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
    public void deleteSportTeamCap(Long sportTeamId, Long captainId) {
        SportTeam sportTeam = sportTeamRepository.findById(sportTeamId).orElse(null);

        if (sportTeam != null) {
            if (sportTeam.getCaptain().getUserId().equals(captainId)) {
                for (User user : sportTeam.getUsers()) {
                    user.setSportTeams(null);
                    user.setParticipationTeam(false);
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
            user.setParticipationTeam(true);
            sportTeamRepository.save(sportTeam);

        }
    }

    @Override
    public void addUserByEmailToSportTeam(Long sportTeamId, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        SportTeam sportTeam = sportTeamRepository.findById(sportTeamId).orElse(null);

        if (user != null && sportTeam != null) {
            sportTeam.getUsers().add(user);
            user.setSportTeams(sportTeam);
            user.setParticipationTeam(true);
            sportTeamRepository.save(sportTeam);
        }
    }

    @Override
    public void RemoveUserByEmailFromSportTeamE(Long sportTeamId, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        SportTeam sportTeam = sportTeamRepository.findById(sportTeamId).orElse(null);

        if (user != null && sportTeam != null) {
            sportTeam.getUsers().remove(user);
            user.setSportTeams(null);
            user.setParticipationTeam(false);
            userRepository.save(user);
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
            user.setParticipationTeam(false);
            sportTeamRepository.save(sportTeam);
        }
    }




    @Override
    public void acceptUserToSportTeam(Long sportTeamId, Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {

            if (user.getSportTeams() != null && user.getSportTeams().getTeamId().equals(sportTeamId)) {

                user.setParticipationTeam(true);
                userRepository.save(user);
            } else {
                throw new IllegalArgumentException("User is not associated with the specified sport team.");
            }
        } else {
            throw new IllegalArgumentException("User not found.");
        }
    }


    @Override
    public void participateSportTeam(Long sportTeamId, Long userId) {
        SportTeam sportTeam = sportTeamRepository.findById(sportTeamId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (sportTeam != null && user != null) {
            if (user.isParticipationTeam()) {
                throw new IllegalStateException("User is already participating in another sport team.");
            }
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
            user.setParticipationTeam(false);
            sportTeamRepository.save(sportTeam);
        }
    }





    @Override
    public List<User> getUsersBySportTeamId(Long sportTeamId) {
        return sportTeamRepository.findUserIdsBySportTeamId(sportTeamId);
    }

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

        long nbPlayers = teamMembers.size();
        teamReservation.setNbPlayers(nbPlayers);

        reservationRepository.save(teamReservation);
        userRepository.saveAll(teamMembers);
    } else {
        throw new IllegalArgumentException("Only the captain can make a team reservation and the provided field must exist.");
    }
}

    @Override
    public int countUsersJoinedInSportTeam(Long teamId) {
        return sportTeamRepository.countUsersJoinedInSportTeam(teamId);
    }
    @Override
    public boolean isUserCaptain(Long userId) {

        List<SportTeam> sportTeams = sportTeamRepository.findByCaptainUserId(userId);
        return !sportTeams.isEmpty();
    }
    @Override
    public boolean isUserCaptainTeam(Long teamId, Long userId) {
        SportTeam sportTeam = sportTeamRepository.findById(teamId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        if (sportTeam != null && user != null) {
            if (sportTeam.getCaptain().getUserId() ==userId) {
                return true;
            }
        }
        return false;
    }


    @Override
    public Long getSportTeamIdByCaptainId(Long captainId) {
        SportTeam sportTeam = sportTeamRepository.findSportTeamsByCaptainUserId(captainId);

        if (sportTeam != null) {
            return sportTeam.getTeamId();
        } else {
            return null; // or throw an exception based on your error handling strategy
        }
    }







}
