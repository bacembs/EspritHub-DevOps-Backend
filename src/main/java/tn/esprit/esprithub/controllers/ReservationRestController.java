package tn.esprit.esprithub.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.esprithub.entities.Reservation;
import tn.esprit.esprithub.services.IReservationService;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/Reservation")
public class ReservationRestController {
    private IReservationService reservationService;
    @PostMapping("/add")
    public Reservation addReservation (@RequestBody Reservation reservation){
        return reservationService.addReservation(reservation);
    }
    @PutMapping("/update")
    public Reservation updateReservation (@RequestBody Reservation reservation){
        return reservationService.updateReservation(reservation);
    }
    @GetMapping("/get/{numReservation}")
    public Reservation getReservation (@PathVariable Long numReservation){
        return reservationService.getReservationById(numReservation);
    }
    @DeleteMapping  ("/delete/{numReservation}")
    public void removeReservation (@PathVariable Long numReservation){
        reservationService.deleteReservation(numReservation);
    }
    @GetMapping  ("/all")
    public List<Reservation> getAll (){
        return reservationService.getAll();}



    @GetMapping("/allWithField")
    public List<Reservation> getAllReservationsWithField() {
        return reservationService.getAllReservationsWithField();
    }

    @GetMapping("/getWithField/{reservationId}")
    public ResponseEntity<Reservation> getReservationWithField(@PathVariable Long reservationId) {
        Reservation reservation = reservationService.getReservationWithField(reservationId);
        if (reservation != null) {
            return ResponseEntity.ok(reservation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping("/users/{userId}/{fieldId}/reserve")
    public ResponseEntity<Reservation> makeReservationForUser(@PathVariable Long userId,
                                                              @PathVariable Long fieldId,
                                                              @RequestBody Reservation reservation) {
        if (reservation == null || reservation.getStartDate() == null || reservation.getEndDate() == null || reservation.getNbPlayers() <= 0 ||
                reservation.getResStatus() == null || reservation.getResType() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Reservation savedReservation = reservationService.addReservationForUser(userId, fieldId, reservation);
        if (savedReservation != null) {
            return ResponseEntity.ok(savedReservation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @DeleteMapping("/users/{userId}/{reservationId}")
    public ResponseEntity<Void> deleteReservationForUser(@PathVariable Long userId,
                                                         @PathVariable Long reservationId) {
        reservationService.deleteReservationForUser(userId, reservationId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{userId}/{reservationId}")
    public ResponseEntity<Reservation> updateReservationForUser(@PathVariable Long userId,
                                                                @PathVariable Long reservationId,
                                                                @RequestBody Reservation updatedReservation) {
        Reservation updated = reservationService.updateReservationForUser(userId, reservationId, updatedReservation);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/users/{userId}/reservations")
    public ResponseEntity<List<Reservation>> getReservationsForUser(@PathVariable Long userId) {
        List<Reservation> reservations = reservationService.getReservationsForUser(userId);
        if (reservations != null && !reservations.isEmpty()) {
            return ResponseEntity.ok(reservations);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
