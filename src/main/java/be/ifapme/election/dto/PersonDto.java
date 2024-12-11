package be.ifapme.election.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PersonDto {
    private Integer id;
    private String nom;
    private String prenom;
    private String registreNational;
    private AdresseDto adresse;
    private String password;
}
