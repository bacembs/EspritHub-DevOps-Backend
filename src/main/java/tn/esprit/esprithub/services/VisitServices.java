package tn.esprit.esprithub.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.esprithub.entities.AvailabilityTimeSlot;
import tn.esprit.esprithub.entities.Housing;
import tn.esprit.esprithub.entities.Visit;
import tn.esprit.esprithub.repositories.IAvailabilityTimeSlotRepository;
import tn.esprit.esprithub.repository.IHousingRepository;
import tn.esprit.esprithub.repositories.IVisitRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VisitServices {
    private IVisitRepository iVisitRepository;
    private IHousingServices iHousingServices;
    private IHousingRepository iHousingRepository;
    private IAvailabilityTimeSlotRepository iAvailabilityTimeSlotRepository;

    public Visit addVisit(Long housingId, Visit visit) {
        // Récupérer le logement correspondant à partir de son ID
        Optional<Housing> housingOptional = iHousingRepository.findById(housingId);
        if (housingOptional.isPresent()) {
            Housing housing = housingOptional.get();
            // Associer la visite au logement
            visit.setHousing(housing);
            if (visit.getEndDateTime() == null) {
                // Si l'heure de fin n'est pas spécifiée, définir une valeur par défaut de startTime + 59 minutes
                LocalDateTime endDateTime = visit.getStartDateTime().plusMinutes(59);
                visit.setEndDateTime(endDateTime);
            }
            return iVisitRepository.save(visit);
        }
        return null;
    }

    public void deleteVisit(Long id){
         iVisitRepository.deleteById(id);
    }
    public Visit updateVisit(Long id, Visit visit){
        Visit existingVisit = iVisitRepository.findById(id).orElse(null);
        existingVisit.setDescription(visit.getDescription());
        existingVisit.setStartDateTime(visit.getStartDateTime());
        existingVisit.setEndDateTime(visit.getEndDateTime());

        existingVisit.setHousing(visit.getHousing());
        existingVisit.setVisiteur(visit.getVisiteur());
        iVisitRepository.save(existingVisit);
        return  existingVisit;

    }
    public Visit getById(Long id){
        return iVisitRepository.findById(id).orElse(null);
    }
    public List<Visit> getAll(){

        return (List<Visit>) iVisitRepository.findAll();
    }

    public boolean isHousingAvailableForVisit(Long housingId, LocalDateTime visitStart, LocalDateTime visitEnd) {
        Optional<Housing> optionalHousing = iHousingRepository.findById(housingId);
        if (optionalHousing.isPresent()) {
            Housing housing = optionalHousing.get();
            List<Visit> visits = housing.getVisitsHousing();
            if (visits == null || visits.isEmpty()) {
                return true; // Aucune visite programmée, le logement est disponible
            }
            // Vérifier si l'horaire de visite chevauche les visites existantes
            for (Visit visit : visits) {
                LocalDateTime existingVisitStart = visit.getStartDateTime();
                LocalDateTime existingVisitEnd = visit.getEndDateTime();
                if (!(visitEnd.isBefore(existingVisitStart) || visitStart.isAfter(existingVisitEnd))) {
                    return false; // Le logement n'est pas disponible à l'horaire de visite
                }
            }
            return true; // Le logement est disponible à l'horaire de visite
        } else {
            return false;
        }
    }
    public List<String> getAvailableVisitSlotsForHousing(Long housingId, LocalDateTime visitDate) {
        // Récupérer le logement
        Housing housing = iHousingRepository.findById(housingId)
                .orElse(null);

        // Récupérer toutes les visites pour ce logement
        List<Visit> visits = housing.getVisitsHousing();

        // Déterminer les plages horaires disponibles
        List<String> availableSlots = new ArrayList<>();

        // Définir les heures d'ouverture et de fermeture
        final LocalTime openingTime = LocalTime.of(9, 0); // Heure d'ouverture (9h00)
        final LocalTime closingTime = LocalTime.of(17, 0); // Heure de fermeture (17h00)

        // Vérifier si l'utilisateur a saisi une heure de visite
        if (visitDate.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            // Si l'heure de visite n'est pas spécifiée, utiliser toute la journée
            visitDate = visitDate.with(openingTime);
        }

        // Initialiser les heures de début et de fin de la journée
        LocalDateTime startTime = visitDate.with(openingTime);
        LocalDateTime endTime = visitDate.with(closingTime);

        // Parcourir les tranches horaires dans la journée
        LocalDateTime currentSlotStart = startTime;
        while (currentSlotStart.plusHours(1).isBefore(endTime)) { // Ajouter une heure pour chaque créneau horaire
            // Utiliser des variables finales pour être utilisées dans l'expression lambda
            final LocalDateTime slotStart = currentSlotStart;
            final LocalDateTime slotEnd = currentSlotStart.plusHours(1);

            // Vérifier si la tranche horaire est disponible
            boolean isSlotAvailable = visits.stream()
                    .noneMatch(visit -> !slotEnd.isBefore(visit.getStartDateTime()) && !slotStart.isAfter(visit.getEndDateTime()));

            // Si la tranche horaire est disponible et est dans les limites horaires
            if (isSlotAvailable && slotStart.toLocalTime().isAfter(openingTime) && slotEnd.toLocalTime().isBefore(closingTime)) {
                availableSlots.add(formatSlot(slotStart, slotEnd));
            }

            // Passer à la prochaine tranche horaire
            currentSlotStart = currentSlotStart.plusHours(1); // Passer à la prochaine heure
        }

        return availableSlots;
    }

    private String formatSlot(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return startDateTime.format(formatter) + " - " + endDateTime.format(formatter);
    }

    public List<AvailabilityTimeSlot> getAvailableTimeSlotsForHousing(Long housingId) {
        // Récupérer le logement
        Housing housing = iHousingRepository.findById(housingId)
                .orElse(null);

        // Récupérer toutes les visites pour ce logement
        List<Visit> visits = housing.getVisitsHousing();

        // Récupérer les plages horaires de disponibilité pour ce logement
        List<AvailabilityTimeSlot> availabilityTimeSlots = housing.getAvailabilityTimeSlots();

        // Initialiser les plages horaires disponibles
        List<AvailabilityTimeSlot> availableTimeSlots = new ArrayList<>();

        // Parcourir les plages horaires de disponibilité
        for (AvailabilityTimeSlot timeSlot : availabilityTimeSlots) {
            // Vérifier si cette plage horaire est disponible
            boolean isAvailable = true;

            // Vérifier si cette plage horaire se chevauche avec une visite
            for (Visit visit : visits) {
                if (isOverlap(visit.getStartDateTime(), visit.getEndDateTime(), timeSlot)) {
                    isAvailable = false;
                    break;
                }
            }

            // Si cette plage horaire est disponible, l'ajouter à la liste des plages horaires disponibles
            if (isAvailable) {
                availableTimeSlots.add(timeSlot);
            }
        }

        return availableTimeSlots;
    }

    // Méthode pour vérifier si une plage horaire se chevauche avec une visite
    private boolean isOverlap(LocalDateTime visitStart, LocalDateTime visitEnd, AvailabilityTimeSlot timeSlot) {
        LocalDateTime timeSlotStart = timeSlot.getStartTime();
        LocalDateTime timeSlotEnd = timeSlot.getEndTime();

        // Vérifier si la date de début de la visite est avant la date de fin du créneau horaire disponible
        // et si la date de début du créneau horaire disponible est avant la date de fin de la visite
        return visitStart.isBefore(timeSlotEnd) && visitEnd.isAfter(timeSlotStart);
    }
    public List<Visit> getVisitsByHousingId(Long housingId) {
        return iVisitRepository.findVisitByHousing_HousingID(housingId);
    }
    public Visit createVisitFromTimeSlotAndHousing(Long timeSlotId, Long housingId) {
        AvailabilityTimeSlot timeSlot = iAvailabilityTimeSlotRepository.findById(timeSlotId).orElse(null);
        Housing housing = iHousingRepository.findById(housingId).orElse(null);

        Visit visit = new Visit();
        visit.setStartDateTime(timeSlot.getStartTime());
        visit.setEndDateTime(timeSlot.getEndTime());
        visit.setHousing(housing);
        visit.setDescription("");

        return iVisitRepository.save(visit);
    }









}
