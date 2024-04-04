package tn.esprit.esprithub.services;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tn.esprit.esprithub.entities.Reservation;
import tn.esprit.esprithub.entities.User;
import tn.esprit.esprithub.repository.IReservationRepository;
import tn.esprit.esprithub.repository.IUserRepository;


import java.util.List;
@Service
@AllArgsConstructor
@Primary
public class ReservationService implements IReservationService{
    private IReservationRepository reservationRepository;
    private IUserRepository userRepository;


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
    public Reservation getReservationWithField(Long reservationId) {
        return reservationRepository.findByIdWithField(reservationId);
    }
    @Override
    public Reservation addReservationForUser(Long userId, Reservation reservation) {
        // Retrieve the user from the database
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            // Associate the user with the reservation
            reservation.getUsers().add(user);
            // Save the reservation
            return reservationRepository.save(reservation);
        } else {
            // Handle case where user is not found
            return null;
        }
    }
    @Override
    public List<Reservation> getAllReservationsWithField() {
        return reservationRepository.findAllWithField();
    }

}
