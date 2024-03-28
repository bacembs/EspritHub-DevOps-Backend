package tn.esprit.projetpi.services;

import tn.esprit.projetpi.entities.Reservation;

import java.util.List;

public interface IReservationService {
    Reservation addReservation(Reservation reservation);
    Reservation updateReservation(Reservation reservation);
    void deleteReservation(Long reservationId);
    Reservation getReservationById(Long reservationId);
    List<Reservation> getAll();

}
