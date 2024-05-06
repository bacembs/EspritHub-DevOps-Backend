package tn.esprit.esprithub.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter

public class AddressComponent {
    @JsonProperty("long_name")
    String longName;
    @JsonProperty("short_name")
    String shortName;
    List<String> types;

    public AddressComponent() {
    }
}