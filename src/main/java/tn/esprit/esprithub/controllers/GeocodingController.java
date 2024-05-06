package tn.esprit.esprithub.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tn.esprit.esprithub.entities.GeocodeObject;
import tn.esprit.esprithub.entities.GeocodeResult;
import tn.esprit.esprithub.services.GeocodingService;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GeocodingController {
    @Autowired
    private GeocodingService geocodingService;


    @RestController
    public class GeocodeController {

        private static final String API_KEY = "AIzaSyAgza--UtekhSDNA9ReNKZ36B8oqN24Xqk";
        private static final String BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json";

        @RequestMapping(path = "/geocode", method = RequestMethod.GET)

        public Map<String, Object> getGeocode(@RequestParam String address) throws IOException {
            OkHttpClient client = new OkHttpClient();
            String encodedAddress = URLEncoder.encode(address, "UTF-8");
            String url = BASE_URL + "?language=en&address=" + encodedAddress + "&key=" + API_KEY;
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            ResponseBody responseBody = client.newCall(request).execute().body();
            ObjectMapper objectMapper = new ObjectMapper();
            GeocodeResult result = objectMapper.readValue(responseBody.string(), GeocodeResult.class);

            // Construire la réponse dans le bon format JSON
            List<Map<String, Object>> locations = new ArrayList<>();
            for (GeocodeObject geocodeObject : result.getResults()) {
                Map<String, Object> location = new LinkedHashMap<>();
                location.put("lat", geocodeObject.getGeometry().getGeocodeLocation().getLatitude());
                location.put("lng", geocodeObject.getGeometry().getGeocodeLocation().getLongitude());
                location.put("formatted_address", geocodeObject.getFormattedAddress());
                locations.add(location);

            }

            // Créer un objet JSON pour la réponse
            Map<String, Object> jsonResponse = new LinkedHashMap<>();
            jsonResponse.put("results", locations);
            jsonResponse.put("status", "OK");

            return jsonResponse;
        }

        @GetMapping("/map")
        public String showMapPage() {
            return "map"; // renvoie le nom de la page HTML (sans extension)
        }

        @GetMapping("/showMap")
        public ModelAndView index() {
            return new ModelAndView("index");
        }
    }
}

