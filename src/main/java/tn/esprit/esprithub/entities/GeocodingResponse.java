package tn.esprit.esprithub.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeocodingResponse {
    private String status;
    private List<Result> results;
    // Getters et setters
}
