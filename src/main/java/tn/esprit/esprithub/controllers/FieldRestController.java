package tn.esprit.esprithub.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.esprithub.entities.Field;
import tn.esprit.esprithub.entities.TypeF;
import tn.esprit.esprithub.services.IFieldService;
import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Field")
public class FieldRestController {
    private IFieldService fieldService;

    @PostMapping("/add")
    public Field addField (@RequestBody Field field){
        field.setTypeField(TypeF.valueOf(field.getTypeField().name()));
        return fieldService.addField(field);
    }
    @PutMapping ("/update")
    public Field updateField (@RequestBody Field field){
        return fieldService.updateField(field);
    }
    @GetMapping  ("/get/{numField}")
    public Field getField (@PathVariable Long numField){
        return fieldService.getFieldById(numField);
    }
    @DeleteMapping  ("/delete/{numField}")
    public void removeField (@PathVariable Long numField){
        fieldService.deleteField(numField);
    }
    @GetMapping  ("/all")
    public List<Field> getAll (){
        return fieldService.getAll();}


}
