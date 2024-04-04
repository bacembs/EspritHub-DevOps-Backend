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
    // New endpoint to make reservation for a specific user
    @PostMapping("/users/{userId}/reserve")
    public ResponseEntity<Reservation> makeReservationForUser(@PathVariable Long userId,
                                                              @RequestBody Reservation reservation) {
        Reservation savedReservation = reservationService.addReservationForUser(userId, reservation);
        if (savedReservation != null) {
            return ResponseEntity.ok(savedReservation);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
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
}
