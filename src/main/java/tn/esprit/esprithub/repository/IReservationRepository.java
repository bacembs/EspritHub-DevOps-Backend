package tn.esprit.esprithub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.esprithub.entities.Reservation;
import tn.esprit.esprithub.entities.Rstatus;
import tn.esprit.esprithub.entities.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation,Long> {

    @Query("SELECT r FROM Reservation r LEFT JOIN FETCH r.fields")
    List<Reservation> findAllWithField();

    @Query("SELECT r, r.fields FROM Reservation r")
    List<Object[]> findAllReservationsWithFieldId();



    @Query("SELECT r FROM Reservation r JOIN FETCH r.fields WHERE r.reservationId = :reservationId")
    Reservation findByIdWithField(@Param("reservationId") Long reservationId);

    List<Reservation> findByUsersUserId(Long userId);

    List<Reservation> findByFieldsFieldId(Long fieldId);
    List<Reservation> findByUsersUserIdAndFieldsFieldId(Long userId, Long fieldId);
    List<Reservation> findByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    long countByUsersUserId(Long userId);

    List<Reservation> findByEndDateBeforeAndResStatus(LocalDateTime date,Rstatus rstatus);

    List<Reservation>findByStartDateBeforeAndEndDateAfter(LocalDateTime date, LocalDateTime datefin );



    @Query("SELECT u.email FROM Reservation r JOIN r.users u WHERE r.reservationId = :reservationId")
    String getUserEmailByReservationId(@Param("reservationId") Long reservationId);

    @Query("SELECT r FROM Reservation r JOIN FETCH r.users WHERE r.reservationId = :reservationId")
    Reservation findByIdWithUsers(@Param("reservationId") Long reservationId);

    // List<Reservation> findByStatus(Rstatus rstatus);

   // @Query("select s from Reservation s  where s.resStatus = :type")
 //   List<Reservation> retrieveReservationsByStatusType(@Param("type") Rstatus typeStatus);

}
