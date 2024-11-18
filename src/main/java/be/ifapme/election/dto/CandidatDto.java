package be.ifapme.election.dto;

import lombok.Data;

@Data
public class CandidatDto {
    private CandidatIdDto id;
    private PersonDto personne;
    private ElectionDto election;
    private PartiDto partit;
    private Integer vote;
}
