package tn.esprit.esprithub.services;

import tn.esprit.esprithub.entities.Feedback;
import tn.esprit.esprithub.entities.User;

import java.util.Optional;

public interface IuserServices {
    User getByNom(String nomuser);

}
