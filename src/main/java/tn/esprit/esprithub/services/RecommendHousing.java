package tn.esprit.esprithub.services;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.esprithub.entities.DistanceCalculator;
import tn.esprit.esprithub.entities.Housing;
import tn.esprit.esprithub.entities.Location;
import tn.esprit.esprithub.repository.IHousingRepository;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j

public class RecommendHousing {
    @Autowired
    private GeocodingService geocodingService;

    @Autowired
    private IHousingRepository ihousingRepository;
    private static final Logger logger = LoggerFactory.getLogger(RecommendHousing.class);

    public List<Housing> recommendHousesNearUser(String userAddress) {

        Location userCoordinates = geocodingService.geocodeAddress(userAddress);
        if (userCoordinates == null) {
//            // Gestion de l'adresse non valide
            return Collections.emptyList();
        }


        List<Housing> allHouses = ihousingRepository.findAll();
        List<Housing> recommendedHouses = new ArrayList<>();
        DistanceCalculator distanceCalculator = new DistanceCalculator();
        double maxDistance = 5.0; // Distance maximale en kilomètres
log.info("max dis");
        for (int i=0;i<allHouses.size();i++){//(Housing housing : allHouses) {
            log.info("if");
            String loc = allHouses.get(i).getLocationHousing();
            log.info(loc);
            Location houseCoordinates = geocodingService.geocodeAddress(loc);
            log.info("geocode");
//            if (houseCoordinates == null) {
//                // Afficher un message de journalisation
//                logger.warn("L'adresse de la maison {} est invalide. La maison est ignorée.", housing.getLocationHousing());
//                // Passer à la maison suivante
//                continue;
//            }
            double distance = distanceCalculator.calculateDistance(userCoordinates, houseCoordinates);
            if (distance <= maxDistance) {
                log.info("bravo");
                 recommendedHouses.add(allHouses.get(i));
            }
            //}


        }
        return recommendedHouses;
    }
}

