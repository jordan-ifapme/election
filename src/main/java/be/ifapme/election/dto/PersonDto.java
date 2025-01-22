package be.ifapme.election.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
public class PersonDto {
    private Integer id;
    private String nom;
    private String prenom;
    private String registreNational;
    private AdresseDto adresse;
    private String password;
    private LocalDate birthDate;
    private String gender;
}
