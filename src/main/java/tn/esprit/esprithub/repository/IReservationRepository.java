package tn.esprit.esprithub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.esprithub.entities.Reservation;
import tn.esprit.esprithub.entities.Rstatus;

import java.util.List;
@Repository
public interface IReservationRepository extends JpaRepository<Reservation,Long> {

    @Query("SELECT r FROM Reservation r JOIN FETCH r.fields")
    List<Reservation> findAllWithField();
    @Query("SELECT r FROM Reservation r JOIN FETCH r.fields WHERE r.reservationId = :reservationId")
    Reservation findByIdWithField(@Param("reservationId") Long reservationId);

   // List<Reservation> findByStatus(Rstatus rstatus);

   // @Query("select s from Reservation s  where s.resStatus = :type")
 //   List<Reservation> retrieveReservationsByStatusType(@Param("type") Rstatus typeStatus);

}
