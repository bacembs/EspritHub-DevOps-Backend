package tn.esprit.esprithub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.esprithub.entities.Field;
import tn.esprit.esprithub.entities.Reservation;
import tn.esprit.esprithub.entities.Rstatus;
import tn.esprit.esprithub.entities.TypeF;

import java.util.List;

public interface IFieldRepository extends JpaRepository<Field,Long> {
    List<Field> findByTypeField(TypeF typeField);

    //@Query("select s from Field s  where s.typeField = :type")
  //  List<Field> retrieveFieldByFieldType(@Param("type") TypeF typeField);

}
