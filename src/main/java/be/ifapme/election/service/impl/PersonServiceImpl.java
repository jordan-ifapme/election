package be.ifapme.election.service.impl;

import be.ifapme.election.Exception.AdressNotFoundException;
import be.ifapme.election.Exception.BusinessException;
import be.ifapme.election.command.CreatePersonCommand;
import be.ifapme.election.dto.PersonDto;
import be.ifapme.election.generator.PersonGenerator;
import be.ifapme.election.model.Adresse;
import be.ifapme.election.model.Personne;
import be.ifapme.election.repository.AdresseRepository;
import be.ifapme.election.repository.PersonRepository;
import be.ifapme.election.service.PersonService;
import be.ifapme.election.utils.ModelMapperUtils;
import org.springframework.stereotype.Service;

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
    public PersonDto createPerson(CreatePersonCommand command) throws BusinessException {
        Adresse adresse = adresseRepository.findById(command.getAdresseId()).orElse(null);

        if (adresse == null) {
            throw new AdressNotFoundException();
        }
        Personne person = Personne.builder()
                .nom(command.getNom())
                .prenom(command.getPrenom())
                .registreNational(command.getRegistreNational())
                .adresse(adresse)
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
}
