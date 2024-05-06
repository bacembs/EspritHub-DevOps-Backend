package tn.esprit.esprithub.services;

import tn.esprit.esprithub.entities.AvailabilityTimeSlot;
import tn.esprit.esprithub.entities.Housing;
import tn.esprit.esprithub.entities.Visit;

import java.time.LocalDateTime;
import java.util.List;

public interface IVisitServices {
    Visit addVisit(Visit housing);
    void deleteVisit(Long id);
    Visit updateVisit(Long id, Visit visit);
    Visit getById(Long id);
    List<Visit> getAll();
    boolean isHousingAvailableForVisit(Long housingId, LocalDateTime visitStart, LocalDateTime visitEnd);
    public List<String> getAvailableVisitSlotsForHousing(Long housingId, LocalDateTime visitDate);
    //List<AvailabilityTimeSlot> findAvailableTimeSlotsForVisit(Long idvisit, Long idhousing);
     List<AvailabilityTimeSlot> getAvailableTimeSlotsWithoutVisitOverlap(Long housingId);
    List<Visit> getVisitsByHousingId(Long housingId);


}
