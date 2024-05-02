package tn.esprit.esprithub.services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.esprithub.entities.Reservation;
import tn.esprit.esprithub.entities.SportTeam;
import tn.esprit.esprithub.entities.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface IReservationService {
    Reservation addReservation(Reservation reservation);
    Reservation updateReservation( Long reservationId,Reservation updatedReservation);
    void deleteReservation(Long reservationId);
    Reservation getReservationById(Long reservationId);
    List<Reservation> getAll();
    void deleteReservationForUser(Long userId, Long reservationId);
    Reservation updateReservationForUser(Long userId, Long reservationId, Reservation updatedReservation);

    Reservation cancelReservation(Long reservationId);

    // List<Reservation> getReservationsForUser(Long userId);
    Reservation addReservationForUser(Long userId,Long fieldId, Reservation reservation);

    Reservation getReservationWithField(Long reservationId);

    List<Object[]> getAllReservationsWithFieldId();

    List<Reservation> getAllReservationsWithField();

    List<Reservation> getReservationsForUser(Long userId);

    List<Reservation> getReservationsByField(Long fieldId);
    List<Reservation> getReservationsByUserAndField(Long userId, Long fieldId);
    List<Reservation> getReservationsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    Long countReservationsByUser(Long userId);
    Reservation addReservationSportTeam(Long sportTeamId, Reservation reservation);
    User getCaptainOfSportTeam(Long sportTeamId);

    Set<User> getUsersByReservation(Long reservationId);

    void cancelReservationForSportTeam(Long reservationId, Long captainId);
    void cancelReservationsForToday();
    void joinReservation(Long userId, Long reservationId);
    List<Reservation> getReservationsWithAvailableSpace();
 //   void updateFinishedReservationsStatus();
    void updateFinishedReservationsStatusAndAssignBadges();

    void sendReservationReminders();

    boolean hasUserJoinedReservation(Long reservationId, Long userId);

    void cancelUserReservation(Long userId, Long reservationId);







    }
