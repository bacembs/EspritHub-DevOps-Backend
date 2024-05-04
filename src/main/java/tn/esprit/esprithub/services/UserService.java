package tn.esprit.esprithub.services;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tn.esprit.esprithub.entities.User;
import tn.esprit.esprithub.repositories.IUserRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Primary
public class UserService implements IUserService {
    private IUserRepository userRepository;
    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }
    
    public String getUserFullName(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getFirstName() + " " + user.getLastName() +" "+ user.getUserId();
        }
        return "";
    }


    @Override
    public Long getUserIdByName(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user.getUserId();
        }
        return null;
    }

    @Override
    public User getByNom(String nomuser) {
        return userRepository.findByUsername(nomuser);
    }
    
}