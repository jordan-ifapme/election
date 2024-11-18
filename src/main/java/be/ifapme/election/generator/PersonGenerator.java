package be.ifapme.election.generator;

import be.ifapme.election.command.CreatePersonCommand;
import be.ifapme.election.service.AdresseService;
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
        command.setNom(generateRandomName());
        command.setPrenom(generateRandomFirstName());
        command.setRegistreNational(generateRandomRegistreNational());
        command.setAdresseId(generateRandomAddressId());

        return command;
    }



    private String generateRandomName() {
        String[] lastNames = {"Dupont", "Martin", "Bernard", "Dubois", "Lefevre"};
        return lastNames[random.nextInt(lastNames.length)];
    }

    private String generateRandomFirstName() {
        String[] firstNames = {"Jean", "Pierre", "Marie", "Sophie", "Claude"};
        return firstNames[random.nextInt(firstNames.length)];
    }

    private String generateRandomRegistreNational() {
        // Génère un numéro à 12 chiffres
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sb.append(String.format("%02d", random.nextInt(100))).append("");
        }
        sb.append(String.format("%03d", random.nextInt(1000))).append("");
        sb.append(String.format("%02d", random.nextInt(100))).append("");
        return sb.toString();
    }

    // Récupère un ID d'adresse valide depuis la base de données
    private Integer generateRandomAddressId() {
        return adresseService.getRandomAdresse();  // Appel au service pour obtenir un ID d'adresse
    }

}
