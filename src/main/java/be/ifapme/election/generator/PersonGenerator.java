package be.ifapme.election.generator;

import be.ifapme.election.command.CreatePersonCommand;
import be.ifapme.election.service.AdresseService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PersonGenerator {

    private final Random random = new Random();
    private final AdresseService adresseService;


    public PersonGenerator(AdresseService adresseService) {
        this.adresseService = adresseService;
    }




    public CreatePersonCommand generateRandomPerson() {
        // Génération de données aléatoires pour la commande
        CreatePersonCommand command = new CreatePersonCommand();
        command.setRegistreNational(generateRandomRegistreNational());
        command.setAdresseId(generateRandomAddressId());
        command.setPassword(
                new BCryptPasswordEncoder().encode("test123")
        );
        return command;
    }


    private String generateRandomRegistreNational() {

        return String.format("%08d", random.nextInt(100000000));
    }

    private Integer generateRandomAddressId() {
        return adresseService.getRandomAdresseId();
    }

}
