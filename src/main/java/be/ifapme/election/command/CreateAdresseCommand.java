package be.ifapme.election.command;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateAdresseCommand {
    private String localite;
    private String codePostal;
    private String rue;
    private String boite;
    private String codePays;
}
