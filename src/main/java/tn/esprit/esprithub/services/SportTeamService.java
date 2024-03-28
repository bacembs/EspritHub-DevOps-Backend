package tn.esprit.projetpi.services;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tn.esprit.projetpi.entities.SportTeam;
import tn.esprit.projetpi.repository.ISportTeamRepository;

import java.util.List;
@Service
@AllArgsConstructor
@Primary
public class SportTeamService implements ISportTeamService{
    private ISportTeamRepository sportTeamRepository;
    @Override
    public SportTeam addSportTeam(SportTeam sportTeam) {
        return sportTeamRepository.save(sportTeam);
    }

    @Override
    public SportTeam updateSportTeam(SportTeam sportTeam) {
        return sportTeamRepository.save(sportTeam);
    }

    @Override
    public void deleteSportTeam(Long sportTeamId) {
        sportTeamRepository.deleteById(sportTeamId);
    }

    @Override
    public SportTeam getSportTeamById(Long sportTeamId) {
        return sportTeamRepository.findById(sportTeamId).orElse(null);
    }

    @Override
    public List<SportTeam> getAll() {
        return (List<SportTeam>) sportTeamRepository.findAll();
    }
}
