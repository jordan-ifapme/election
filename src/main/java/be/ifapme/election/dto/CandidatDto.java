package be.ifapme.election.dto;

import be.ifapme.election.model.CandidatId;
import be.ifapme.election.model.Election;
import be.ifapme.election.model.Partit;
import be.ifapme.election.model.Personne;
import lombok.Data;
import lombok.Getter;

@Data
public class CandidatDto {
    private CandidatId id;
    private Personne personne;
    private Election election;
    private Partit partit;
    private Integer vote;
    @Override
    public String toString() {
        return "CandidatDto{" +
                "id=" + id +
                "personne=" + personne +
                ", election=" + election +
                ", partit=" + partit +
                ", vote=" + vote +
                '}';
    }
}
