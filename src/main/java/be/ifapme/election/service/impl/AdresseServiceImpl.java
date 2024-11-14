package be.ifapme.election.service.impl;

import be.ifapme.election.command.CreateAdresseCommand;
import be.ifapme.election.dto.AdresseDto;
import be.ifapme.election.model.Adresse;
import be.ifapme.election.repository.AdresseRepository;
import be.ifapme.election.service.AdresseService;
import be.ifapme.election.utils.ModelMapperUtils;
import org.springframework.stereotype.Service;

@Service
public class AdresseServiceImpl implements AdresseService {

    private final AdresseRepository adresseRepository;

    public AdresseServiceImpl(AdresseRepository adresseRepository) {
        this.adresseRepository = adresseRepository;
    }

    @Override
    public AdresseDto createAdresse(CreateAdresseCommand command) {
        Adresse adresse = Adresse.builder()
                .boite(command.getBoite())
                .rue(command.getRue())
                .localite(command.getLocalite())
                .codePostal(command.getCodePostal()).build();
        Adresse createdAdresse = adresseRepository.save(adresse);
        return ModelMapperUtils
                .getInstance()
                .map(createdAdresse, AdresseDto.class);
    }
}
