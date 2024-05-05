package tn.esprit.esprithub.services;



import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.esprithub.entities.*;
import tn.esprit.esprithub.mailRequest.MailRequest;
import tn.esprit.esprithub.repository.IFieldRepository;
import tn.esprit.esprithub.repository.IReservationRepository;
import tn.esprit.esprithub.repository.ISportTeamRepository;
import tn.esprit.esprithub.repository.IUserRepository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Primary
public class ReservationService implements IReservationService{
    private IReservationRepository reservationRepository;
    private IUserRepository userRepository;
    private IFieldRepository fieldRepository;
    private ISportTeamRepository sportTeamRepository;




    private EmailService emailService;

    @Autowired
    private WeatherService weatherService;

    @Override
    @Scheduled(cron = "0 0 1 * * *")
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
   // @Scheduled(fixedDelay = 60000
    @Scheduled(cron = "0 0 1 * * *") // EveryDay at 01AM
    public void updateFinishedReservationsStatusAndAssignBadges() {
        LocalDateTime now = LocalDateTime.now();
        List<Reservation> reservations = reservationRepository.findByEndDateBeforeAndResStatus(now, Rstatus.finished);
        for (Reservation reservation : reservations) {
            reservation.setResStatus(Rstatus.finished);
            reservationRepository.save(reservation);
        }


        List<User> usersWithUpdatedBadges = userRepository.findUsersWithUpdatedBadges();
        for (User user : usersWithUpdatedBadges) {
            int numReservations = user.getReservations().size();
            if (numReservations >= 5) {
                user.setBadge(Badge.DIAMOND);
            } else if (numReservations >= 3) {
                user.setBadge(Badge.GOLD);
            } else if (numReservations >= 1) {
                user.setBadge(Badge.SILVER);
            }
            userRepository.save(user);
        }
    }




    @Override
    public Reservation addReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation updateReservation( Long reservationId,Reservation updatedReservation) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);

        if (reservation != null) {
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
    public List<Object[]> getAllReservationsWithFieldId() {
        return reservationRepository.findAllReservationsWithFieldId();
    }

    @Override
    public Reservation getReservationWithField(Long reservationId) {
        return reservationRepository.findByIdWithField(reservationId);
    }

    @Override
    public Reservation cancelReservation(Long reservationId){
       Reservation reservation= reservationRepository.findById(reservationId).orElse(null);
        reservation.setResStatus(Rstatus.cancelled);
        return reservationRepository.save(reservation);
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
    reservation.setNbPlayers((long) reservation.getUsers().size());
    user.getReservations().add(reservation);
    userRepository.save(user);
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

@Override
public void joinReservation(Long userId, Long reservationId) {
    Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID " + reservationId));

    if (reservation.getNbPlayers() >= reservation.getFields().getCapacityField()) {
        throw new IllegalStateException("No space available in the reservation.");
    }

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID " + userId));
    reservation.getUsers().add(user);
    user.getReservations().add(reservation);
    reservation.setNbPlayers(reservation.getNbPlayers() + 1);
    reservationRepository.save(reservation);
    userRepository.save(user);
}

    @Override
    public void cancelUserReservation(Long userId, Long reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID " + reservationId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID " + userId));

        reservation.getUsers().remove(user);
        user.getReservations().remove(reservation);

        reservation.setNbPlayers(reservation.getNbPlayers() - 1);

        reservationRepository.save(reservation);
        userRepository.save(user);
    }

    @Override
    public List<Reservation> getReservationsWithAvailableSpace() {

        List<Reservation> reservations = reservationRepository.findAll();


        return reservations.stream()
                .filter(reservation -> {
                    if (reservation.getNbPlayers() != null) {
                        return reservation.getNbPlayers() < reservation.getFields().getCapacityField();
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }



//    @Scheduled(cron = "0 0 10 * * *")
//    public void sendReservationReminders() {
//        List<Reservation> allReservations = reservationRepository.findAll();
//
//        LocalDate tomorrow = LocalDate.now().plusDays(1);
//
//        List<Reservation> reservationsForTomorrow = allReservations.stream()
//                .filter(reservation -> reservation.getStartDate().toLocalDate().equals(tomorrow))
//                .collect(Collectors.toList());
//
//        for (Reservation reservation : reservationsForTomorrow) {
//            String userEmail = getUserEmailFromReservation(reservation);
//            if (userEmail != null) {
//                sendReminderEmail(userEmail, reservation.getStartDate().toString());
//            }
//        }
//    }
    @Override
    @Scheduled(cron = "0 0 10 * * *")
    public void sendReservationReminders() {
        List<Reservation> allReservations = reservationRepository.findAll();
        LocalDate tomorrow = LocalDate.now().plusDays(1);

//        List<Reservation> reservationsForTomorrow = allReservations.stream()
//                .filter(reservation -> reservation.getStartDate().toLocalDate().equals(tomorrow))
//                .collect(Collectors.toList());
        List<Reservation> reservationsForTomorrow = allReservations.stream()
                .filter(reservation ->
                        reservation.getStartDate().toLocalDate().equals(tomorrow) &&
                                reservation.getResStatus() == Rstatus.confirmed)
                .collect(Collectors.toList());

        for (Reservation reservation : reservationsForTomorrow) {
            String userEmail = getUserEmailFromReservation(reservation);
            if (userEmail != null) {
                String subject = "Reservation Reminder";
                String text = "This is a reminder for your reservation scheduled for " + reservation.getStartDate().toString();
                emailService.sendEmail(userEmail, subject, text);
            }
        }
    }
    public String getUserEmailFromReservation(Reservation reservation) {
        return reservationRepository.getUserEmailByReservationId(reservation.getReservationId());
    }

    @Override
    public void sendReminderEmail(String to, String reservationDate) {
        String subject = "Reservation Reminder";
        String text = "This is a reminder for your reservation scheduled for " + reservationDate;

        MailRequest mailRequest = new MailRequest();
        mailRequest.setTo(to);
        mailRequest.setSubject(subject);
        mailRequest.setText(text);

        emailService.sendReservationReminder(mailRequest);
    }

    //    public void sendReminderEmail(String to, String reservationDate) {
//        // Construct the email message
//        String subject = "Reservation Reminder";
//        String text = "This is a reminder for your reservation scheduled for " + reservationDate;
//
//
//        emailService.sendEmail(to, subject, text);
//    }
    @Override
    public boolean hasUserJoinedReservation(Long reservationId, Long userId) {
        Reservation reservation = reservationRepository.findByIdWithUsers(reservationId);
        return reservation != null && reservation.getUsers().stream().anyMatch(user -> user.getUserId().equals(userId));
    }
}
