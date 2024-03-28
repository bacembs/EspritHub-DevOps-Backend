package tn.esprit.projetpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.projetpi.entities.Field;
import tn.esprit.projetpi.entities.Reservation;
import tn.esprit.projetpi.entities.Rstatus;
import tn.esprit.projetpi.entities.TypeF;

import java.util.List;

public interface IFieldRepository extends JpaRepository<Field,Long> {
   // List<Field> findByTypeField(TypeF typeField);

    //@Query("select s from Field s  where s.typeField = :type")
  //  List<Field> retrieveFieldByFieldType(@Param("type") TypeF typeField);

}
