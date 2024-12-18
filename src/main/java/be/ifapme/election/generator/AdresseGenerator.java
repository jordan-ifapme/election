package be.ifapme.election.generator;

import be.ifapme.election.command.CreateAdresseCommand;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class AdresseGenerator {

    private final Random random = new Random();

    private String generateRandomLocalites() {
        String[] localites = {"Bruxelles", "Liège", "Namur", "Anvers", "Gand"};
        return localites[random.nextInt(localites.length)];
    }

    private String generateRandomRues() {
        String[] rues = {"Rue de la Loi", "Avenue Louise", "Boulevard du Roi Albert", "Rue Neuve", "Place Royale"};
        return rues[random.nextInt(rues.length)];
    }

    public CreateAdresseCommand generateRandomAdresse() {
        CreateAdresseCommand command = new CreateAdresseCommand();
        command.setLocalite(generateRandomLocalites());
        command.setCodePostal(String.format("%04d", random.nextInt(10000)));
        command.setRue(generateRandomRues());
        command.setCodePays("BE");
        command.setBoite("Boite " + (random.nextInt(250) + 1));
        return command;
    }
}
