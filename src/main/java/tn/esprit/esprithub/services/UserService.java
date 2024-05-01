package tn.esprit.esprithub.services;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tn.esprit.esprithub.entities.User;
import tn.esprit.esprithub.repositories.IUserRepository;

import java.util.List;

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
}