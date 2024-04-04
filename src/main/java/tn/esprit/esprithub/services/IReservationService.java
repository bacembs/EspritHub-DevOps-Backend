package tn.esprit.esprithub.services;

import tn.esprit.esprithub.entities.Reservation;

import java.util.List;

public interface IReservationService {
    Reservation addReservation(Reservation reservation);
    Reservation updateReservation(Reservation reservation);
    void deleteReservation(Long reservationId);
    Reservation getReservationById(Long reservationId);
    List<Reservation> getAll();
    Reservation addReservationForUser(Long userId, Reservation reservation);

    Reservation getReservationWithField(Long reservationId);

    List<Reservation> getAllReservationsWithField();




    }
