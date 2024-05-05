package tn.esprit.esprithub.services;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tn.esprit.esprithub.entities.Field;
import tn.esprit.esprithub.repository.IFieldRepository;
import tn.esprit.esprithub.repository.IReservationRepository;

import java.time.LocalDate;
import java.util.List;
@Service
@AllArgsConstructor
@Primary
public class FieldService implements IFieldService{
    private IFieldRepository fieldRepository;
    @Override
    public Field addField(Field field) {
        return fieldRepository.save(field);
    }

    @Override
    public Field updateField(Field field) {
        return fieldRepository.save(field);
    }

    @Override
    public void deleteField(Long fieldId) {
        fieldRepository.deleteById(fieldId);
    }

    @Override
    public Field getFieldById(Long fieldId) {
        return fieldRepository.findById(fieldId).orElse(null);
    }

    @Override
    public List<Field> getAll() {
        return (List<Field>) fieldRepository.findAll();
    }

//    @Override
//    public List<Field> searchFieldsByLocation(String locationField) {
//        return fieldRepository.findByLocation(locationField);
//    }



}
