package tn.esprit.esprithub.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.esprithub.entities.User;
import tn.esprit.esprithub.repository.UserRepository;
import java.util.Optional;


@AllArgsConstructor
@Service
public class UserServices {
    private UserRepository userrepos;
    public String getUserFullName(Long userId) {
        Optional<User> userOptional = userrepos.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getFirstName() + " " + user.getLastName() +" "+ user.getUserId();
        }
        return "";
    }


    public Long getUserIdByName(String username) {
        User user = userrepos.findByUsername(username);
        if (user != null) {
            return user.getUserId();
        }
        return null;
    }

}
