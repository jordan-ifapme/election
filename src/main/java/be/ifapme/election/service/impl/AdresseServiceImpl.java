package be.ifapme.election.service.impl;

import be.ifapme.election.command.CreateAdresseCommand;
import be.ifapme.election.dto.AdresseDto;
import be.ifapme.election.generator.AdresseGenerator;
import be.ifapme.election.model.Adresse;
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
    private final Random random = new Random();

    public AdresseServiceImpl(AdresseRepository adresseRepository, AdresseGenerator adresseGenerator) {
        this.adresseRepository = adresseRepository;
        this.adresseGenerator = adresseGenerator;
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



    // adresse aleatoire id
    @Transactional(readOnly = true)
    public Integer getRandomAdresse() {
        // Récupère tous les objets Adresse depuis la base de données
        List<Adresse> adresses = adresseRepository.findAll();

        // Si aucune adresse n'existe, retourner null ou une valeur par défaut
        if (adresses.isEmpty()) {
            return null;  // ou une valeur par défaut, selon le cas
        }

        // Choisir une adresse aléatoire parmi celles récupérées
        return random.nextInt(adresses.size());
    }

    @Override
    public List<AdresseDto> generateRandomAdresses(int nbrs) {
        List<AdresseDto> adresses = new ArrayList<>();
        for (int i = 0; i < nbrs; i++) {
            CreateAdresseCommand command = adresseGenerator.generateRandomAdresse();
            AdresseDto adresse = this.createAdresse(command);
            adresses.add(adresse);
        }
        return adresses;
    }


}
