package be.ifapme.election.command;

import lombok.Getter;
import lombok.Setter;

@Setter // pour le generator
@Getter
public class CreatePersonCommand {
    private String nom;
    private String prenom;
    private String registreNational;
    private Integer adresseId;
}