package be.ifapme.election.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdresseDto {
    private Integer id;
    private String localite;
    private String codePostal;
    private String rue;
    private String boite;
}
