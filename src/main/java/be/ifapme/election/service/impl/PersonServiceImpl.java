package be.ifapme.election.service.impl;

import be.ifapme.election.Exception.*;
import be.ifapme.election.command.CreatePersonCommand;
import be.ifapme.election.dto.PersonDto;
import be.ifapme.election.generator.PersonGenerator;
import be.ifapme.election.model.Adresse;
import be.ifapme.election.model.Personne;
import be.ifapme.election.model.PersonneApi;
import be.ifapme.election.repository.AdresseRepository;
import be.ifapme.election.repository.PersonRepository;
import be.ifapme.election.service.PersonService;
import be.ifapme.election.utils.ModelMapperUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;


@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final AdresseRepository adresseRepository;
    private final PersonGenerator personGenerator;

    public PersonServiceImpl(PersonRepository personRepository, AdresseRepository adresseRepository, PersonGenerator personGenerator) {
        this.personRepository = personRepository;
        this.adresseRepository = adresseRepository;
        this.personGenerator = personGenerator;
    }
    @Override
    public Personne findById(Integer id) {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public PersonDto createPerson(CreatePersonCommand command) throws BusinessException {

        if (command.getRegistreNational() == null || command.getRegistreNational().length() != 8) {
            throw new NationalNumberException();
        }
        if (personRepository.findByRegistreNational(command.getRegistreNational()).isPresent()) {
            throw new NationalNumberAlreadyExistException();
        }
        // chance de 10% de pas fonctionner
        if (Math.random() < 0.1) {
            throw new NationalNumberAlreadyExistException();
        }

        String serviceUrl = "https://personne-express.onrender.com/api/person/" + command.getRegistreNational();
        RestTemplate restTemplate = new RestTemplate();
        PersonneApi personneApi;
        try {
            personneApi = restTemplate.getForObject(serviceUrl, PersonneApi.class);
        } catch (Exception e) {
            throw new BusinessException("Erreur lors de l'appel au service externe : " + e.getMessage());
        }

        if (command.getAdresseId() == null) {
            throw new AdressNotFoundException();
        }

        Adresse adresse = adresseRepository.findById(command.getAdresseId()).orElseThrow(AdressNotFoundException::new);


        Personne person = Personne.builder()
                .nom(personneApi.getLastName())
                .prenom(personneApi.getFirstName())
                .registreNational(command.getRegistreNational())
                .birthDate(personneApi.getBirthDate())
                .gender(personneApi.getGender())
                .adresse(adresse)
                .password(new BCryptPasswordEncoder().encode(command.getPassword()))
                .is_admin(false)
                .build();

        Personne personSaved = personRepository.save(person);
        return ModelMapperUtils
                .getInstance()
                .map(personSaved, PersonDto.class);
    }

    @Override
    public List<PersonDto> generateRandomPersons(int nbrs) throws BusinessException {
        List<PersonDto> persons = new ArrayList<>();
        for (int i = 0; i < nbrs; i++) {
            CreatePersonCommand command = personGenerator.generateRandomPerson();
            PersonDto person = this.createPerson(command);
            persons.add(person);
        }
        return persons;
    }

    @Override
    public Personne findByUserName(String username) throws BusinessException {
        return personRepository.findByRegistreNational(username)
                .orElse(null);
    }
}
