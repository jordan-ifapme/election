package be.ifapme.election.dto;

import be.ifapme.election.model.Election;
import be.ifapme.election.model.Partit;
import be.ifapme.election.model.Personne;
import lombok.Getter;

@Getter
public class CandidatDto {
    private Personne personne;
    private Election election;
    private Partit partit;
    private Integer vote;
}
