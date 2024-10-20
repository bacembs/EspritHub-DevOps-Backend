package tn.esprit.esprithub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.esprithub.entities.Complaint;

public interface IComplaintRepository extends JpaRepository<Complaint,Long> {
}