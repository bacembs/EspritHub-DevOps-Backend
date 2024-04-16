package tn.esprit.esprithub.services;



import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.esprithub.entities.*;
import tn.esprit.esprithub.repository.IFieldRepository;
import tn.esprit.esprithub.repository.IReservationRepository;
import tn.esprit.esprithub.repository.ISportTeamRepository;
import tn.esprit.esprithub.repository.IUserRepository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
@Primary
public class ReservationService implements IReservationService{
    private IReservationRepository reservationRepository;
    private IUserRepository userRepository;
    private IFieldRepository fieldRepository;
    private ISportTeamRepository sportTeamRepository;


    @Autowired
    private WeatherService weatherService;

    @Override
    @Scheduled(cron = "0 0 1 * * *") // EveryDay at 01AM
    public void cancelReservationsForToday() {
        if (weatherService.isRainyToday()) {
            LocalDate currentDate = LocalDate.now();
            LocalDateTime startOfDay = currentDate.atStartOfDay();
            LocalDateTime endOfDay = currentDate.atTime(23, 59);

            List<Reservation> reservationsForToday = reservationRepository.findByStartDateBetween(startOfDay, endOfDay);


            for (Reservation reservation : reservationsForToday) {
                reservation.setResStatus(Rstatus.cancelled);
                reservationRepository.save(reservation);
            }
        }
    }




    @Override
    public Reservation addReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    @Override
    public Reservation getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId).orElse(null);
    }

    @Override
    public List<Reservation> getAll() {
        return (List<Reservation>) reservationRepository.findAll();
    }

    @Override
    public List<Reservation> getAllReservationsWithField() {
        return reservationRepository.findAllWithField();
    }

    @Override
    public Reservation getReservationWithField(Long reservationId) {
        return reservationRepository.findByIdWithField(reservationId);
    }

    @Override
    public Reservation addReservationForUser(Long userId, Long fieldId, Reservation reservation) {

        if (!userRepository.existsById(userId) || !fieldRepository.existsById(fieldId)) {
            return null;
        }

        User user = userRepository.findById(userId).orElse(null);
        Field field = fieldRepository.findById(fieldId).orElse(null);

        if (user == null || field == null) {
            return null;
        }

        reservation.setUsers(new HashSet<>(Collections.singletonList(user)));
        reservation.setFields(field);



        reservation.updateStatus();


        user.getReservations().add(reservation);
        userRepository.save(user);
        long nbPlayers = reservation.getUsers().size();
        reservation.setNbPlayers(nbPlayers);

        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservationForUser(Long userId, Long reservationId) {
        User user = userRepository.findById(userId).orElse(null);
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);

        if (user != null && reservation != null) {
            user.getReservations().remove(reservation);
            userRepository.save(user);
            reservationRepository.delete(reservation);
        }
    }

    @Override
    public Reservation updateReservationForUser(Long userId, Long reservationId, Reservation updatedReservation) {
        User user = userRepository.findById(userId).orElse(null);
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);

        if (user != null && reservation != null) {
            reservation.setStartDate(updatedReservation.getStartDate());
            reservation.setEndDate(updatedReservation.getEndDate());
            reservation.setNbPlayers(updatedReservation.getNbPlayers());
            reservation.setResStatus(updatedReservation.getResStatus());
            reservation.setResType(updatedReservation.getResType());

            return reservationRepository.save(reservation);
        }

        return null;
    }

    @Override
    public List<Reservation> getReservationsForUser(Long userId) {
        return reservationRepository.findByUsersUserId(userId);
    }

    @Override
    public List<Reservation> getReservationsByField(Long fieldId) {
        return reservationRepository.findByFieldsFieldId(fieldId);
    }

    @Override
    public List<Reservation> getReservationsByUserAndField(Long userId, Long fieldId) {
        return reservationRepository.findByUsersUserIdAndFieldsFieldId(userId, fieldId);
    }

    @Override
    public List<Reservation> getReservationsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return reservationRepository.findByStartDateBetween(startDate, endDate);
    }

    @Override
    public Long countReservationsByUser(Long userId) {
        return reservationRepository.countByUsersUserId(userId);
    }

//    @Override
//    public Reservation addReservationSportTeam(Long sportTeamId, Reservation reservation) {
//        SportTeam sportTeam = sportTeamRepository.findById(sportTeamId)
//                .orElseThrow(() -> new EntityNotFoundException("SportTeam not found with ID " + sportTeamId));
//
//        Set<User> users = new HashSet<>(sportTeam.getUsers());
//        reservation.setUsers(users);
//
//        return reservationRepository.save(reservation);
//    }

    public Reservation addReservationSportTeam(Long sportTeamId, Reservation reservation) {
        User captain = getCaptainOfSportTeam(sportTeamId);
        reservation.setUsers(new HashSet<>(Collections.singletonList(captain)));
        return reservationRepository.save(reservation);
    }

    @Override
    public User getCaptainOfSportTeam(Long sportTeamId) {
        SportTeam sportTeam = sportTeamRepository.findById(sportTeamId)
                .orElseThrow(() -> new EntityNotFoundException("SportTeam not found with ID " + sportTeamId));
        return sportTeam.getCaptain();
    }



    @Override
    public Set<User> getUsersByReservation(Long reservationId) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            return reservation.getUsers();
        }
        return Collections.emptySet();
    }

    @Override
    public void cancelReservationForSportTeam(Long reservationId, Long captainId) {

        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation not found");
        }


        User captain = userRepository.findById(captainId).orElse(null);
        if (captain == null) {
            throw new IllegalArgumentException("Captain not found");
        }


        SportTeam sportTeam = captain.getSportTeams();
        if (sportTeam == null) {
            throw new IllegalStateException("Captain is not associated with any sport team");
        }


        if (!sportTeam.getCaptain().equals(captain)) {
            throw new IllegalArgumentException("Only the captain can cancel the reservation for the sport team");
        }


        Set<User> teamMembers = sportTeam.getUsers();
        for (User member : teamMembers) {
            member.getReservations().remove(reservation);
            userRepository.save(member);
        }


        reservationRepository.delete(reservation);
    }

}
