package tn.esprit.projetpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.projetpi.entities.Reservation;
import tn.esprit.projetpi.entities.Rstatus;

import java.util.List;
@Repository
public interface IReservationRepository extends JpaRepository<Reservation,Long> {

   // List<Reservation> findByStatus(Rstatus rstatus);

   // @Query("select s from Reservation s  where s.resStatus = :type")
 //   List<Reservation> retrieveReservationsByStatusType(@Param("type") Rstatus typeStatus);

}
