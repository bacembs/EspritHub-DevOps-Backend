package tn.esprit.esprithub.services;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.esprithub.entities.Housing;
import tn.esprit.esprithub.repository.IHousingRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class housingServices  implements IhousingServices{
    final private IHousingRepository housingrepo;
    @Override
    public List<Housing> getAll() {
        return (List<Housing>) housingrepo.findAll();
    }
}
