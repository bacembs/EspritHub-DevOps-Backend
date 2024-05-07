package tn.esprit.esprithub.controllers;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.esprithub.entities.AvailabilityTimeSlot;
import tn.esprit.esprithub.entities.Housing;
import tn.esprit.esprithub.entities.Visit;
import tn.esprit.esprithub.services.HousingServices;
import tn.esprit.esprithub.services.IVisitServices;
import tn.esprit.esprithub.services.VisitServices;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/Visit")
@RestController

public class VisitRestController {

    @Autowired
    private VisitServices visitServices;



    @PostMapping("/add/{id}")
    public Visit addHousing(@PathVariable Long id,@RequestBody Visit visit){
        return visitServices.addVisit(id,visit);
    }
    @GetMapping("/getById/{id}")
    public Visit getById(@PathVariable Long id) {
        return visitServices.getById(id);
    }
    @DeleteMapping("/delete/{idVisit}")
    public void removeHousing(@PathVariable Long idVisit) {
        visitServices.deleteVisit(idVisit);
    }

    @GetMapping("/all")
    public List<Visit> getAll() {
        return visitServices.getAll();
    }
    @PutMapping("/update/{id}")
    public Visit updateVisit(@PathVariable Long id,@RequestBody Visit visit){
        return  visitServices.updateVisit(id,visit);
    }
    @GetMapping("/checkAvailability")
    public Boolean isHousingAvailableForVisit(@RequestParam Long housingId,
                                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime visitStart,
                                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime visitEnd) {
        return  visitServices.isHousingAvailableForVisit(housingId, visitStart, visitEnd);

    }
    @GetMapping("/getAvailableVisitSlotsForHousing")
    public List<String> getAvailableVisitSlotsForHousing(@RequestParam Long housingId,  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime visitDate){
        return visitServices.getAvailableVisitSlotsForHousing(housingId,visitDate);
    }
    @GetMapping("/getAvailableTimeSlotsWithoutVisitOverlap/{idhousing}")
    public List<AvailabilityTimeSlot> getAvailableTimeSlotsWithoutVisitOverlap(@PathVariable Long idhousing){
        return  visitServices.getAvailableTimeSlotsForHousing(idhousing);
    }
    @PostMapping("/create/{timeSlotId}/{housingId}")
    public Visit createVisitFromTimeSlotAndHousing(@PathVariable Long timeSlotId, @PathVariable Long housingId) {
        Visit visit = visitServices.createVisitFromTimeSlotAndHousing(timeSlotId, housingId);
        return  visit;
    }
}
