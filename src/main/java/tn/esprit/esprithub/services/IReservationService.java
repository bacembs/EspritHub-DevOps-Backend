package tn.esprit.esprithub.services;

import tn.esprit.esprithub.entities.Reservation;

import java.util.List;

public interface IReservationService {
    Reservation addReservation(Reservation reservation);
    Reservation updateReservation(Reservation reservation);
    void deleteReservation(Long reservationId);
    Reservation getReservationById(Long reservationId);
    List<Reservation> getAll();
    void deleteReservationForUser(Long userId, Long reservationId);
    Reservation updateReservationForUser(Long userId, Long reservationId, Reservation updatedReservation);

   // List<Reservation> getReservationsForUser(Long userId);
    Reservation addReservationForUser(Long userId,Long fieldId, Reservation reservation);

    Reservation getReservationWithField(Long reservationId);

    List<Reservation> getAllReservationsWithField();

    List<Reservation> getReservationsForUser(Long userId);




    }
