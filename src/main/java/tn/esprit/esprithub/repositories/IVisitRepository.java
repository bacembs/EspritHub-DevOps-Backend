package tn.esprit.esprithub.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.esprithub.entities.Visit;

import java.util.List;

public interface IVisitRepository extends CrudRepository<Visit,Long> {
    List<Visit> findVisitByHousing_HousingID(Long housingId);

}
