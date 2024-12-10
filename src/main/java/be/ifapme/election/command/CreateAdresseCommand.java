package be.ifapme.election.command;

import lombok.Getter;
import lombok.Setter;

@Setter // // pour le generator
@Getter
public class CreateAdresseCommand {
    private String localite;
    private String codePostal;
    private String rue;
    private String boite;
    private String codePays;
}
