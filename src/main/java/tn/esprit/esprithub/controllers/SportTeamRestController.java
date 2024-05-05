package tn.esprit.esprithub.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.esprithub.entities.Reservation;
import tn.esprit.esprithub.entities.SportTeam;
import tn.esprit.esprithub.entities.User;
import tn.esprit.esprithub.services.ISportTeamService;
import tn.esprit.esprithub.services.SportTeamService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/SportTeam")
public class SportTeamRestController {
    private ISportTeamService sportTeamService;
    @PostMapping("/add")
    public SportTeam addSportTeam (@RequestBody SportTeam sportTeam){
        return sportTeamService.addSportTeam(sportTeam);
    }
    @PutMapping("/update")
    public SportTeam updateSportTeam (@RequestBody SportTeam sportTeam){
        return sportTeamService.updateSportTeam(sportTeam);
    }


    //Ok angular
    @GetMapping("/get/{numSportTeam}")
    public SportTeam getSportTeam (@PathVariable Long numSportTeam){
        return sportTeamService.getSportTeamById(numSportTeam);
    }


    @DeleteMapping  ("/delete/{numSportTeam}")
    public void removeSportTeam (@PathVariable Long numSportTeam){
        sportTeamService.deleteSportTeam(numSportTeam);
    }


    //ok angular
    @GetMapping  ("/all")
    public List<SportTeam> getAll (){
        return sportTeamService.getAll();}


    //ok angular
    @PostMapping("/add-with-photo/{captainId}")
    public ResponseEntity<SportTeam> addSportTeamWithPhoto(
            @RequestParam("nameTeam") String teamName,
            @PathVariable Long captainId,
            @RequestParam("logo") MultipartFile photoFile) {
        System.out.println("Received photo file: " + photoFile);
        // Call the method to save the SportTeam with the photo
        SportTeam savedTeam = sportTeamService.addSportTeamCap3(teamName, captainId, photoFile);
        return ResponseEntity.ok(savedTeam);
    }

    //ok angular but no photo
    @PutMapping("/update-with-photo/{sportTeamId}")
    public ResponseEntity<SportTeam> updateSportTeamWithPhoto(
            @PathVariable Long sportTeamId,
            @RequestParam("nameTeam") String teamName,
            @RequestParam(value = "logo", required = false) MultipartFile photoFile) {

        System.out.println("Received photo file: " + photoFile);
        SportTeam updatedTeam = sportTeamService.updateSportTeamCapWithPhoto(teamName,sportTeamId , photoFile);
        if (updatedTeam != null) {
            return ResponseEntity.ok(updatedTeam);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    //ok angular
    @PostMapping("/addCap/{captainId}")
    public SportTeam addSportTeamCap (@RequestBody SportTeam sportTeam,@PathVariable Long captainId){
        return sportTeamService.addSportTeamCap(sportTeam,captainId);
    }


    @PutMapping("/updateCap/{sportTeamId}")
    public SportTeam updateSportTeamCap (@RequestBody SportTeam sportTeam,@PathVariable Long sportTeamId){
        return sportTeamService.updateSportTeamCap(sportTeam,sportTeamId);
    }






    @DeleteMapping("/deleteCap/{numSportTeam}/{captainId}")
    public void deleteSportTeamCap(@PathVariable Long numSportTeam, @PathVariable Long captainId) {
        sportTeamService.deleteSportTeamCap(numSportTeam, captainId);
    }



    //ok angular ama zeyda
    @PostMapping("/addUser/{sportTeamId}/{userId}")
    public ResponseEntity<String> addUserToSportTeam(@PathVariable Long sportTeamId, @PathVariable Long userId) {
        sportTeamService.addUserToSportTeam(sportTeamId, userId);
        return ResponseEntity.ok("User added to sport team successfully.");
    }



//ok angular
    @PostMapping("/{sportTeamId}/add-user")
    public ResponseEntity<String> addUserByEmailToSportTeamEmail(
            @PathVariable Long sportTeamId,
            @RequestParam String userEmail) {
        System.out.println(userEmail);
        sportTeamService.addUserByEmailToSportTeam(sportTeamId, userEmail);
        return ResponseEntity.ok("User added to sport team successfully.");
    }


    //ok angular
    @PostMapping("/removeUser/{sportTeamId}/{userId}")
    public ResponseEntity<String> removeUserFromSportTeam(@PathVariable Long sportTeamId, @PathVariable Long userId) {
        sportTeamService.removeUserFromSportTeam(sportTeamId, userId);
        return ResponseEntity.ok("User removed from sport team successfully.");
    }

    @PostMapping("/{sportTeamId}/accept-user/{userId}")
    public ResponseEntity<String> acceptUserToSportTeam(@PathVariable Long sportTeamId, @PathVariable Long userId) {
        try {
            sportTeamService.acceptUserToSportTeam(sportTeamId, userId);
            return new ResponseEntity<>("User accepted to the sport team successfully.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/participateSportTeam/{sportTeamId}")
    public ResponseEntity<String> participateSportTeam(@PathVariable Long sportTeamId, @RequestHeader("userId") Long userId) {
        try {
            sportTeamService.participateSportTeam(sportTeamId, userId);
            return ResponseEntity.ok("Participated in sport team successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/cancelParticipateSportTeam/{sportTeamId}")
    public ResponseEntity<String> cancelParticipationSportTeam(@PathVariable Long sportTeamId, @RequestHeader("userId") Long userId) {
        sportTeamService.cancelParticipation(sportTeamId, userId);
        return ResponseEntity.ok("Cancelled participation from sport team successfully.");
    }






    @GetMapping("/{sportTeamId}/users")
    public ResponseEntity<?> getUsersBySportTeamId(@PathVariable Long sportTeamId) {
        List<User> users = sportTeamService.getUsersBySportTeamId(sportTeamId);
        if (!users.isEmpty()) {
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/{sportTeamId}/reservations")
    public ResponseEntity<String> makeTeamReservation(
            @PathVariable Long sportTeamId,
            @RequestParam Long captainId,
            @RequestParam Long fieldId,
            @RequestBody Reservation reservation) {
        try {
            sportTeamService.makeTeamReservation(sportTeamId, captainId, fieldId, reservation);
            return ResponseEntity.ok("Team reservation created successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while making the team reservation.");
        }
    }


    @GetMapping("/{sportTeamId}/user-count")
    public ResponseEntity<Integer> countUsersJoinedInSportTeam(@PathVariable Long sportTeamId) {
        int userCount = sportTeamService.countUsersJoinedInSportTeam(sportTeamId);
        return ResponseEntity.ok(userCount);
    }

    @GetMapping("/user/{userId}/is-captain")
    public ResponseEntity<Boolean> isUserCaptain(@PathVariable Long userId) {
        boolean isCaptain = sportTeamService.isUserCaptain(userId);
        return ResponseEntity.ok(isCaptain);
    }

    @GetMapping("/captain/{captainId}")
    public ResponseEntity<Long> getSportTeamIdByCaptainId(@PathVariable Long captainId) {
        Long sportTeamId = sportTeamService.getSportTeamIdByCaptainId(captainId);
        if (sportTeamId != null) {
            return ResponseEntity.ok(sportTeamId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/teams/{teamId}/captain/{userId}")
    public ResponseEntity<Boolean> isUserCaptainTeam(@PathVariable Long teamId, @PathVariable Long userId) {
        boolean isCaptain = sportTeamService.isUserCaptainTeam(teamId, userId);
        return new ResponseEntity<>(isCaptain, HttpStatus.OK);
    }



}


