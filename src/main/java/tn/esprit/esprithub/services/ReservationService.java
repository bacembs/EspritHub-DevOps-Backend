package tn.esprit.esprithub.services;



import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tn.esprit.esprithub.entities.Field;
import tn.esprit.esprithub.entities.Reservation;
import tn.esprit.esprithub.entities.User;
import tn.esprit.esprithub.repository.IFieldRepository;
import tn.esprit.esprithub.repository.IReservationRepository;
import tn.esprit.esprithub.repository.IUserRepository;


import java.util.Collections;
import java.util.HashSet;
import java.util.List;
@Service
@AllArgsConstructor
@Primary
public class ReservationService implements IReservationService{
    private IReservationRepository reservationRepository;
    private IUserRepository userRepository;
    private IFieldRepository fieldRepository;




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


}
