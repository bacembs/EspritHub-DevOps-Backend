package tn.esprit.esprithub.services;

import tn.esprit.esprithub.entities.AvailabilityTimeSlot;
import tn.esprit.esprithub.entities.Housing;

import java.util.List;

public interface IHousingServices {
    Housing addHousing(Housing housing);
    void deleteHousing(Long idHousing);
    Housing updateHousing(Long idHousing,Housing housing);
    Housing getById(Long idHousing);
    List<Housing> getAll();
     Housing upHousing(Housing housing);
     int getHousingWithOwnerPhoneNumber(Long id);
     void addAvailabilityTimeSlotsToHousing(Long housingId, List<AvailabilityTimeSlot> timeSlots);
     List<String> getAvailableTimeSlotsForHousing(Long housingId);
    void addAvailabilityTimeSlotsToHousing(Long housingId,AvailabilityTimeSlot timeSlot);
    List<Housing> getHousingsByOwnerId(Long ownerId);
}
