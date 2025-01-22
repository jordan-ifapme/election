package be.ifapme.election.service.impl;

import be.ifapme.election.Exception.*;
import be.ifapme.election.command.CreatePersonCommand;
import be.ifapme.election.dto.PersonDto;
import be.ifapme.election.generator.PersonGenerator;
import be.ifapme.election.model.Adresse;
import be.ifapme.election.model.Personne;
import be.ifapme.election.repository.AdresseRepository;
import be.ifapme.election.repository.PersonRepository;
import be.ifapme.election.service.PersonService;
import be.ifapme.election.utils.ModelMapperUtils;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final AdresseRepository adresseRepository;
    private final PersonGenerator personGenerator;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://personne-express.onrender.com/api/with-error/person")
            .build();

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
            throw new InvalidNationalRegistrationLengthExceptionException();
        }

        Adresse adresse = adresseRepository.findById(command.getAdresseId()).orElse(null);
        Personne personne = personRepository.findByRegistreNational(command.getRegistreNational()).orElse(null);

        if (adresse == null) {
            throw new AdressNotFoundException();
        }

        if (personne == null) {
            JsonNode personneData = webClient.get()
                    .uri("/{code}", command.getRegistreNational())
                    .retrieve()
                    .bodyToMono(JsonNode.class)  // Deserialize as JsonNode
                    .block();

            if (personneData != null) {
                String firstName = personneData.get("firstName").asText();
                String lastName = personneData.get("lastName").asText();
                String birthDate = personneData.get("birthDate").asText(); // Format ISO8601
                String gender = personneData.get("gender").asText();

                // Build the Personne object with data from API
                PasswordEncoder encoder = new BCryptPasswordEncoder();
                String encodedPassword = encoder.encode("123");

                Personne person = Personne.builder()
                        .nom(lastName)
                        .prenom(firstName)
                        .registreNational(command.getRegistreNational())
                        .adresse(adresse)
                        .password(encodedPassword)
                        .is_admin(false)
                        .birth_date(LocalDateTime.parse(birthDate, DateTimeFormatter.ISO_DATE_TIME))
                        .gender(gender)
                        .build();

                Personne personSaved = personRepository.save(person);
                return ModelMapperUtils
                        .getInstance()
                        .map(personSaved, PersonDto.class);
            } else {
                throw new ErrorNISSException();
            }
        }

        throw new PersonFoundException();
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
