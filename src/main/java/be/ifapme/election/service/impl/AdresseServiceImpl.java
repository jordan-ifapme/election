package be.ifapme.election.service.impl;

import be.ifapme.election.Exception.BusinessException;
import be.ifapme.election.command.CreateAdresseCommand;
import be.ifapme.election.dto.AdresseDto;
import be.ifapme.election.generator.AdresseGenerator;
import be.ifapme.election.model.Adresse;
import be.ifapme.election.model.Pays;
import be.ifapme.election.repository.AdresseRepository;
import be.ifapme.election.service.AdresseService;
import be.ifapme.election.utils.ModelMapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class AdresseServiceImpl implements AdresseService {

    private final AdresseRepository adresseRepository;
    private final AdresseGenerator adresseGenerator;
    private final CountryServiceImpl countryService;
    private final Random random = new Random();

    public AdresseServiceImpl(AdresseRepository adresseRepository, AdresseGenerator adresseGenerator, CountryServiceImpl countryService) {
        this.adresseRepository = adresseRepository;
        this.adresseGenerator = adresseGenerator;
        this.countryService = countryService;
    }

    @Override
    public AdresseDto createAdresse(CreateAdresseCommand command) throws BusinessException {
        Pays pays = countryService.createPays(command.getCodePays());
        Adresse adresse = Adresse.builder()
                .boite(command.getBoite())
                .rue(command.getRue())
                .localite(command.getLocalite())
                .codePostal(command.getCodePostal())
                .codePays(pays.getCodePays())
                .build();
        Adresse createdAdresse = adresseRepository.save(adresse);
        return ModelMapperUtils
                .getInstance()
                .map(createdAdresse, AdresseDto.class);
    }



    @Transactional(readOnly = true)
    public Integer getRandomAdresseId() {
        List<Adresse> adresses = adresseRepository.findAll();
        if (adresses.isEmpty()) {
            return null;
        }
        int randomAdresseIndex = random.nextInt(adresses.size());
        return adresses.get(randomAdresseIndex).getId();
    }

    @Override
    public List<AdresseDto> generateRandomAdresses(int nbrs) throws BusinessException {
        List<AdresseDto> adresses = new ArrayList<>();
        for (int i = 0; i < nbrs; i++) {
            CreateAdresseCommand command = adresseGenerator.generateRandomAdresse();
            AdresseDto adresse = this.createAdresse(command);
            adresses.add(adresse);
        }
        return adresses;
    }


}
