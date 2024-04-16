package tn.esprit.esprithub.repository;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.esprithub.entities.Housing;
import tn.esprit.esprithub.entities.Transaction;

public interface IHousingRepository extends CrudRepository<Housing, Long> {
}
