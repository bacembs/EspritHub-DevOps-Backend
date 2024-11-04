package tn.esprit.esprithub.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.esprithub.entities.User;
import tn.esprit.esprithub.services.IUserService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/User")
public class UserRestController {
    private IUserService userService;

    @PostMapping("/add")
    public User addUser (@RequestBody User user){
        return userService.addUser(user);
    }
    @PutMapping("/update")
    public User updateUser (@RequestBody User user){
        return userService.updateUser(user);
    }
    @GetMapping  ("/{userId}")
    public User getUser (@PathVariable Long userId){
        return userService.getUserById(userId);
    }
    @DeleteMapping  ("/delete/{userId}")
    public void removeUser (@PathVariable Long userId){
        userService.deleteUser(userId);
    }
    @GetMapping  ("/all")
    public List<User> getAll (){
        return userService.getAll();}
    @GetMapping("/user")
    public User getbyuser(@RequestBody User user){
        return userService.getByNom(user.getUsername());
    }
    @GetMapping("/users/{userId}")
    public String getUserAddress(@PathVariable Long userId) {
        // Récupérer l'adresse du user à partir de son ID
        String address = userService.getUserAddressById(userId);


            return address;

    }
}