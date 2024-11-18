package be.ifapme.election.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class VoteDto {
    private PersonDto personne;
    private ElectionDto election;
}
