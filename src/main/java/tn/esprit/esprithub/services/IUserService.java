package tn.esprit.esprithub.services;

import tn.esprit.esprithub.entities.User;

import java.util.List;

public interface IUserService {
    User addUser(User user);
    User updateUser(User user);
    void deleteUser(Long userId);
    User getUserById(Long userId);
    List<User> getAll();
    User getByNom(String nomuser);
    public Long getUserIdByName(String username);

}