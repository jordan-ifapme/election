package be.ifapme.election.dto;

import be.ifapme.election.model.Adresse;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonDto {
    private Integer id;
    private String nom;
    private String prenom;
    private String registreNational;
    private Adresse adresse;
}
