package tn.esprit.esprithub.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tn.esprit.esprithub.entities.GeocodingResponse;
import tn.esprit.esprithub.entities.Geometry;
import tn.esprit.esprithub.entities.Location;
import tn.esprit.esprithub.entities.Result;

import java.util.Objects;

@Service

public class GeocodingService {
    @Value("AIzaSyAgza--UtekhSDNA9ReNKZ36B8oqN24Xqk")
    private String googleMapsApiKey;
    private final RestTemplate restTemplate;
    public GeocodingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public Location getCoordinates(String address) {
        try {
            String apiUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + googleMapsApiKey;
            ResponseEntity<GeocodingResponse> response = restTemplate.getForEntity(apiUrl, GeocodingResponse.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                GeocodingResponse responseBody = response.getBody();
                if (responseBody != null && "OK".equals(responseBody.getStatus())) {
                    Result result = responseBody.getResults().get(0);
                    Geometry geometry = result.getGeometry();
                    return geometry.getLocation();
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Vous pouvez utiliser un logger à la place
        }
        return null;
    }
    public Location geocodeAddress(String address) {
        String apiUrl = "https://maps.googleapis.com/maps/api/geocode/json?address="+ address +"&key="+googleMapsApiKey;
        RestTemplate restTemplate = new RestTemplate();
        GeocodingResponse response = restTemplate.getForObject(apiUrl, GeocodingResponse.class, address, googleMapsApiKey);
        if (response != null && response.getResults() != null && response.getResults().size() > 0) {
            Result result = response.getResults().get(0);
            return new Location(result.getGeometry().getLocation().getLat(), result.getGeometry().getLocation().getLng());
        }
        return null;
    }
}
