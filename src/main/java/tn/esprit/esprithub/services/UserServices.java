package tn.esprit.esprithub.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.esprithub.entities.User;
import tn.esprit.esprithub.repository.IfeedBackRepository;
import tn.esprit.esprithub.repository.IuserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServices implements IuserServices {

    private final IuserRepository userRepository;

    @Override
    public User getByNom(String nomuser) {
        return userRepository.findByUsername(nomuser);
    }
}
