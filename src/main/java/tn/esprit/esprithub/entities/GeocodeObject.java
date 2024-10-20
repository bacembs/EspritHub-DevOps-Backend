package tn.esprit.esprithub.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodeObject {
    @JsonProperty("place_id")
    String placeId;
    @JsonProperty("address_components")
    List<AddressComponent> addressComponents;
    @JsonProperty("formatted_address")
    String formattedAddress;
    GeocodeGeometry geometry;

    public GeocodeObject() {
    }
}
