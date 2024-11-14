package be.ifapme.election.command;

import lombok.Data;
import lombok.Getter;

@Getter
public class CreatePersonCommand {
    private String nom;
    private String prenom;
    private String registreNational;
    private Integer adresseId;
}