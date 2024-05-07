package tn.esprit.esprithub.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.esprithub.entities.AvailabilityTimeSlot;
import tn.esprit.esprithub.entities.Housing;
import tn.esprit.esprithub.repository.IHousingRepository;

import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class HousingServices implements IHousingServices {



    private IHousingRepository housingRepository;


    @Override
    public Housing addHousing(Housing housing) {


        return housingRepository.save(housing);
    }
    @Override
    public Housing upHousing(Housing housing) {


        return housingRepository.save(housing);
    }

    @Override
    public void deleteHousing(Long idHousing) {
        housingRepository.deleteById(idHousing);

    }

//    @Override
//    public Housing updateHousing(Long idHousing ,Housing housing) {
//        return housingRepository.save(housing);
//    }
@Override
public Housing updateHousing(Long id, Housing housing) {
    Housing existingHousing = getById(id);
    existingHousing.setTypeHousing(housing.getTypeHousing());
    existingHousing.setDescriptionHousing(housing.getDescriptionHousing());
    existingHousing.setLocationHousing(housing.getLocationHousing());
    existingHousing.setAvailabilityHousing(housing.getAvailabilityHousing());
    existingHousing.setPriceHousing(housing.getPriceHousing());
    return housingRepository.save(existingHousing);
}

    @Override
    public Housing getById(Long idHousing) {


        return housingRepository.findById(idHousing).orElse(null);
    }

    @Override
    public List<Housing> getAll() {
        return (List<Housing>) housingRepository.findAll();
    }


@Override
    public int getHousingWithOwnerPhoneNumber(Long id) {
    Housing housing = housingRepository.getById(id);
        if (housing.getOwner() != null) {
            // Accéder au numéro de téléphone de l'owner de ce housing
             int phone = housing.getOwner().getPhone();
            return phone;
        }


    return 0;
}
    public void addAvailabilityTimeSlotsToHousing(Long housingId, List<AvailabilityTimeSlot> timeSlots) {
        Housing housing = housingRepository.findById(housingId)
                .orElse(null);
        for (AvailabilityTimeSlot timeSlot : timeSlots) {
            timeSlot.setHousing(housing);
        }

        // Ajouter les nouveaux AvailabilityTimeSlot au Housing
        assert housing != null;
        housing.getAvailabilityTimeSlots().addAll(timeSlots);

        // Mettre à jour le Housing dans la base de données
        housingRepository.save(housing);
    }

    public void addAvailabilityTimeSlotToHousing(Long housingId,AvailabilityTimeSlot timeSlot){
        Housing housing = housingRepository.findById(housingId)
                .orElse(null);

            timeSlot.setHousing(housing);


        // Ajouter les nouveaux AvailabilityTimeSlot au Housing
        assert housing != null;
        housing.getAvailabilityTimeSlots().add(timeSlot);

        // Mettre à jour le Housing dans la base de données
        housingRepository.save(housing);

    }



        public List<String> getAvailableTimeSlotsForHousing(Long housingId) {
            Housing housing = housingRepository.findById(housingId)
                    .orElse(null);


            List<AvailabilityTimeSlot> availabilityTimeSlots = housing.getAvailabilityTimeSlots();

            // Convertir les objets AvailabilityTimeSlot en format de chaîne approprié
            List<String> availableTimeSlots = new ArrayList<>();
            for (AvailabilityTimeSlot timeSlot : availabilityTimeSlots) {
                String slot = timeSlot.getStartTime() + " - " + timeSlot.getEndTime();
                availableTimeSlots.add(slot);
            }

            return availableTimeSlots;
        }

    @Override
    public void addAvailabilityTimeSlotsToHousing(Long housingId, AvailabilityTimeSlot timeSlot) {

    }
    public List<Housing> getHousingsByOwnerId(Long ownerId) {
        return housingRepository.findHousingByOwner_UserId(ownerId);
    }
}



