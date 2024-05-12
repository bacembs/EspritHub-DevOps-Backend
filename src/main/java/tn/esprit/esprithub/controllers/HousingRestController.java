package tn.esprit.esprithub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.esprithub.entities.*;
import tn.esprit.esprithub.repository.IHousingRepository;
import tn.esprit.esprithub.repositories.IVisitRepository;
import tn.esprit.esprithub.services.GeocodingService;
import tn.esprit.esprithub.services.HousingServices;
import tn.esprit.esprithub.services.RecommendHousing;
import tn.esprit.esprithub.services.VisitServices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RequestMapping("/Housing")
@RestController
public class HousingRestController {


    @Autowired
    private HousingServices housingServices;
    @Autowired
    private VisitServices visitServices;
    @Autowired
    private GeocodingService geocodingService;
    @Autowired
    private RecommendHousing recommendHousing;
    @Autowired
    private IHousingRepository iHousingRepository;


    @GetMapping("/coordinates")
    public Location getCoordinates(@RequestParam String address) {
        return geocodingService.geocodeAddress(address);
    }

    @GetMapping("/recommend-houses")
    public List<Housing> recommendHousesForUser(@RequestParam String userAddress) {

        for (Housing housing : recommendHousing.recommendHousesNearUser(userAddress)) {
            String imagePath = uploadDirectory + housing.getImages().get(0);
            List<String> list = new ArrayList<>();
            try {
                byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                list.add(base64Image);
                System.out.println(base64Image);
                housing.setImages(list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return recommendHousing.recommendHousesNearUser(userAddress);
    }

    public static String uploadDirectory = "./src/main/resources/static/photos/";
    @Autowired
    private IVisitRepository iVisitRepository;
    //System.getProperty("user.dir")+"/src/main/resources/static/images";

////    // public static String uploadDirectory = System.getProperty("user.dir") + "C:" + File.separator +"xampp" + File.separator + "htdocs" + File.separator + "img" + File.separator + "imgPI";

    //    @PostMapping("/add")
//    public Housing addHousing(@RequestPart Housing housing, @RequestParam("images") List<MultipartFile> files) throws IOException {
//        List<String> imageNames = new ArrayList<>();
//
//       for (MultipartFile file : files) {
//            String originalFilename = file.getOriginalFilename();
//            Path fileNameAndPath = Paths.get(uploadDirectory, originalFilename);
//            Files.write(fileNameAndPath, file.getBytes());
//            imageNames.add(originalFilename);
//        }
//
//        housing.setImages(imageNames);
//       return housingServices.addHousing(housing);
//  }
    @PostMapping("/add")
    public Housing addHousing(@RequestParam("typeHousing") TypeH typeHousing,
                              @RequestParam("descriptionHousing") String descriptionHousing,
                              @RequestParam("locationHousing") String locationHousing,
                              @RequestParam("availabilityHousing") boolean availabilityHousing,
                              @RequestParam("priceHousing") float priceHousing,
                              @RequestParam("images") List<MultipartFile> files,
                              @RequestParam("userId") Long userId) throws IOException {
        Housing housing = new Housing();
        housing.setTypeHousing(typeHousing);
        housing.setDescriptionHousing(descriptionHousing);
        housing.setLocationHousing(locationHousing);
        housing.setAvailabilityHousing(availabilityHousing);
        housing.setPriceHousing(priceHousing);
        User user=new User();
        user.setUserId(userId);
        housing.setOwner(user); // Set the user ID

        List<String> imageNames = new ArrayList<>();
        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDirectory, originalFilename);
            Files.write(fileNameAndPath, file.getBytes());
            imageNames.add(originalFilename);
        }

        housing.setImages(imageNames);

        return housingServices.addHousing(housing);
    }



//    @PostMapping("/add")
//    public Housing addHousing(@RequestBody Housing housing){
//        return housingServices.addHousing(housing);
//
//    }


// Appelez la méthode addHousing de HousingServices pour enregistrer le logement
//   return HousingServices.addHousing(housing);
//}


    //    @PutMapping("/update")
//    public Housing updateHousing(@RequestBody Housing housing){
//        return housingServices.updateHousing(housing);
//    }
    @PutMapping("/updateHousing/{id}")
    public Housing updateEvent(@PathVariable Long id, @RequestBody Housing housing) {
        return housingServices.updateHousing(id, housing);
    }

    @GetMapping("/get/{idHousing}")
    public Housing getHousing(@PathVariable Long idHousing) {
        Housing housing =housingServices.getById(idHousing);
            String imagePath = uploadDirectory + housing.getImages().get(0);
            List<String> list = new ArrayList<>();
            try {
                byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                list.add(base64Image);
                System.out.println(base64Image);
                housing.setImages(list);
            } catch (IOException e) {
                e.printStackTrace();
            }


        return housing;
    }

    @DeleteMapping("/delete/{idHousing}")
    public void removeHousing(@PathVariable Long idHousing) {
        housingServices.deleteHousing(idHousing);
    }

    @GetMapping("/all")
    public List<Housing> getAll() {

        for (Housing housing : housingServices.getAll()) {
            String imagePath = uploadDirectory + housing.getImages().get(0);
            List<String> list = new ArrayList<>();
            try {
                byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                list.add(base64Image);
                System.out.println(base64Image);
                housing.setImages(list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return housingServices.getAll();
    }
    @GetMapping("/phone/{id}")
    public int getAllHousingsWithOwnerPhoneNumbers(@PathVariable Long id){
        return housingServices.getHousingWithOwnerPhoneNumber(id);
    }


    @GetMapping("/ping")
    @ResponseBody
    public String hello_world() {
        return "Hello World!";
    }

    @PutMapping("/updateInterview/{id}")
    public Housing updateHousing(@PathVariable Long id, @RequestPart Housing updatedHousing , @RequestParam("images") List<MultipartFile> files) throws IOException {
        // Retrieve the existing housing
        Housing existingHousing = housingServices.getById(id);

        // Update the existing housing's fields with the values from the updatedHousing object
        existingHousing.setLocationHousing(updatedHousing.getLocationHousing());
        existingHousing.setDescriptionHousing(updatedHousing.getDescriptionHousing());
        // Add other fields as necessary

        // Handle image uploads
        List<String> imageNames = new ArrayList<>();
        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDirectory, originalFilename);
            Files.write(fileNameAndPath, file.getBytes());
            imageNames.add(originalFilename);
        }
        // Update the existing housing's images
        existingHousing.setImages(imageNames);

        // Save the updated existing housing
        return housingServices.upHousing(existingHousing);
    }
    @PostMapping("/housing/addTimeSlot/{housingId}")
    public void addAvailabilityTimeSlotsToHousing(@PathVariable Long housingId, @RequestBody List<AvailabilityTimeSlot> timeSlots) {
        housingServices.addAvailabilityTimeSlotsToHousing(housingId, timeSlots);
    }
//    @PostMapping("/housing/addTimeSlot/{housingId}")
//    public void addAvailabilityTimeSlotToHousing(@PathVariable Long housingId, @RequestBody AvailabilityTimeSlot timeSlot) {
//        housingServices.addAvailabilityTimeSlotToHousing(housingId, timeSlot);
//    }
    @GetMapping("/availableTimeSlots/{housingId}")
    @ResponseBody
    public List<AvailabilityTimeSlot> getAvailableTimeSlotsForHousing(@PathVariable Long housingId){
        return housingServices.getAvailableTimeSlotsForHousing(housingId);
    }
    @GetMapping("/visits/{housingId}")
    public ResponseEntity<List<Visit>> getVisitsByHousingId(@PathVariable Long housingId) {

        List<Visit> visits = visitServices.getVisitsByHousingId(housingId);
        return ResponseEntity.ok(visits);
    }
    @GetMapping("/housingsByOwner/{ownerId}")
    public List<Housing> getHousingsByOwner(@PathVariable Long ownerId) {

        for (Housing housing : housingServices.getHousingsByOwnerId(ownerId)) {
            String imagePath = uploadDirectory + housing.getImages().get(0);
            List<String> list = new ArrayList<>();
            try {
                byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                list.add(base64Image);
                System.out.println(base64Image);
                housing.setImages(list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return housingServices.getHousingsByOwnerId(ownerId);
    }


}

