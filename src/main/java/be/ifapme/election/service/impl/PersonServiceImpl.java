package be.ifapme.election.service.impl;

import be.ifapme.election.Exception.*;
import be.ifapme.election.command.CreatePersonCommand;
import be.ifapme.election.dto.PersonDto;
import be.ifapme.election.generator.PersonGenerator;
import be.ifapme.election.model.Adresse;
import be.ifapme.election.model.Personne;
import be.ifapme.election.model.PersonneAPI;
import be.ifapme.election.repository.AdresseRepository;
import be.ifapme.election.repository.PersonRepository;
import be.ifapme.election.service.PersonService;
import be.ifapme.election.service.PersonneFeignClient;
import be.ifapme.election.utils.ModelMapperUtils;
import feign.FeignException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final AdresseRepository adresseRepository;
    private final PersonGenerator personGenerator;
    private final PersonneFeignClient personneFeignClient;

    public PersonServiceImpl(PersonRepository personRepository, AdresseRepository adresseRepository, PersonGenerator personGenerator, PersonneFeignClient personneFeignClient) {
        this.personRepository = personRepository;
        this.adresseRepository = adresseRepository;
        this.personGenerator = personGenerator;
        this.personneFeignClient = personneFeignClient;
    }

    @Override
    public Personne findById(Integer id) {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public PersonDto createPerson(CreatePersonCommand command) throws BusinessException {

        if (command.getRegistreNational().length() != 8) {
            throw new invalidNationalRegisterException();
        }

        Personne personExist = personRepository.findByRegistreNational(command.getRegistreNational()).orElse(null);

        if (personExist != null) {
            throw new PersonneAlreadyExistException();
        }

        Adresse adresse = adresseRepository.findById(command.getAdresseId()).orElse(null);

        if (adresse == null) {
            throw new AdressNotFoundException();
        }
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(command.getPassword());

        PersonneAPI personneAPI;

        try {
            personneAPI = personneFeignClient.getPersonneByRegistreNational(command.getRegistreNational());
        } catch (FeignException e) {
            throw new PersonneFeignClientNotFoundException();
        }

        Personne person = Personne.builder()
                .nom(personneAPI.getFirstName())
                .prenom(personneAPI.getLastName())
                .registreNational(command.getRegistreNational())
                .adresse(adresse)
                .password(encodedPassword)
                .is_admin(false)
                .gender(personneAPI.getGender())
                .birthDate(personneAPI.getBirthDate())
                .build();
        Personne personSaved = personRepository.save(person);
        return ModelMapperUtils
                .getInstance()
                .map(personSaved, PersonDto.class);
    }

    public String modifyPerson(int id) throws BusinessException {
        Personne person = personRepository.findById(id).orElse(null);
        if (person == null) {
            throw new PersonneNotFoundException(id);
        }
        PersonneAPI personneAPI;
        try {
            personneAPI = personneFeignClient.getPersonneByRegistreNational(person.getRegistreNational());
        } catch (FeignException e) {
            throw new PersonneFeignClientNotFoundException();
        }
        person.setNom(personneAPI.getFirstName());
        person.setPrenom(personneAPI.getLastName());
        person.setGender(personneAPI.getGender());
        person.setBirthDate(personneAPI.getBirthDate());
        personRepository.save(person);
        return "Les changements ont bien été effectués";
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
