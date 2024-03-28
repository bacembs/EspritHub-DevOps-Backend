package tn.esprit.projetpi.services;

import tn.esprit.projetpi.entities.Field;
import tn.esprit.projetpi.entities.Reservation;

import java.util.List;

public interface IFieldService {
    Field addField(Field field);
    Field updateField(Field field);
    void deleteField(Long fieldId);
    Field getFieldById(Long fieldId);
    List<Field> getAll();
}
