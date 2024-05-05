package tn.esprit.esprithub.services;

import tn.esprit.esprithub.entities.Field;
import tn.esprit.esprithub.entities.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface IFieldService {
    Field addField(Field field);
    Field updateField(Field field);
    void deleteField(Long fieldId);
    Field getFieldById(Long fieldId);
    List<Field> getAll();

  //  List<Field> searchFieldsByLocation(String location);


}
