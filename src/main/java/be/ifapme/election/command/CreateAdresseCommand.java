package be.ifapme.election.command;

import lombok.Getter;

@Getter
public class CreateAdresseCommand {
    private String localite;
    private String codePostal;
    private String rue;
    private String boite;
}
