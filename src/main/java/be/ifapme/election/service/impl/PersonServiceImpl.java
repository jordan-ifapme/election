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
import org.springframework.http.HttpStatusCode;
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
        // Vérification de la longueur du registre national
        if (command.getRegistreNational() == null || command.getRegistreNational().length() != 8) {
            throw new InvalidNationalRegistrationLengthExceptionException();
        }

        // Recherche de l'adresse
        Adresse adresse = adresseRepository.findById(command.getAdresseId()).orElse(null);
        if (adresse == null) {
            throw new AdressNotFoundException();
        }

        // Recherche de la personne
        Personne personne = personRepository.findByRegistreNational(command.getRegistreNational()).orElse(null);

        // Si la personne existe déjà
        if (personne != null) {
            throw new PersonFoundException();
        }

        // Si la personne n'existe pas, on la récupère de l'API
        try {
            JsonNode personneData = webClient.get()
                    .uri("/{code}", command.getRegistreNational())
                    .retrieve()
                    .onStatus(
                            HttpStatusCode::is5xxServerError, // Capture des erreurs 5xx
                            response -> {
                                // Gestion des erreurs serveur
                                return response.bodyToMono(String.class)
                                        .flatMap(errorMessage -> {
                                            try {
                                                throw new ExternalApiException();
                                            } catch (ExternalApiException e) {
                                                throw new RuntimeException(e);
                                            }
                                        });
                            })
                    .onStatus(
                            HttpStatusCode::is4xxClientError, // Capture des erreurs 4xx
                            response -> {
                                // Gestion des erreurs client
                                return response.bodyToMono(String.class)
                                        .flatMap(errorMessage -> {
                                            try {
                                                throw new ExternalApiException();
                                            } catch (ExternalApiException e) {
                                                throw new RuntimeException(e);
                                            }
                                        });
                            })
                    .bodyToMono(JsonNode.class) // Désérialisation du corps de la réponse en JsonNode
                    .block(); // Bloquer pour obtenir la réponse synchronisée

            if (personneData == null) {
                throw new ErrorNISSException(); // Si les données sont vides
            }

            // Extraire les informations de la réponse de l'API
            String firstName = personneData.get("firstName").asText();
            String lastName = personneData.get("lastName").asText();
            String birthDate = personneData.get("birthDate").asText(); // Format ISO8601
            String gender = personneData.get("gender").asText();

            // Construction de l'objet Personne avec les données de l'API
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

            // Sauvegarde de la personne dans la base de données
            Personne personSaved = personRepository.save(person);

            // Retourner la réponse sous forme de DTO
            return ModelMapperUtils
                    .getInstance()
                    .map(personSaved, PersonDto.class);
        } catch (Exception e) {
            // Gestion des exceptions et erreurs liées à l'API
            throw new ExternalApiException();
        }
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
