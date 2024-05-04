package tn.esprit.esprithub.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.esprithub.entities.Feedback;
import tn.esprit.esprithub.entities.Housing;
import tn.esprit.esprithub.services.IfeedBackServices;
import tn.esprit.esprithub.services.IhousingServices;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@AllArgsConstructor
@RequestMapping("/housing")
public class HousingRestController {
    private IhousingServices servicehousing ;

    @GetMapping("/all")
    public ResponseEntity<List<Housing>> getAllhousings() {
        List<Housing> Housings = servicehousing.getAll();

        if (Housings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null); // Ou un message d'erreur approprié dans le corps de la réponse
        }

        return ResponseEntity.ok(Housings);
    }

}
