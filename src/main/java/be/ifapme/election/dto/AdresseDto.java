package be.ifapme.election.dto;

import lombok.Data;

@Data
public class AdresseDto {
    private Integer id;
    private String localite;
    private String codePostal;
    private String rue;
    private String boite;
    private String codePays;
}
