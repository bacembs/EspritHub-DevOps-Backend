package tn.esprit.esprithub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.esprithub.entities.Filee;
import tn.esprit.esprithub.entities.Internship;

public interface FileRepository extends JpaRepository<Filee, Long> {



}
