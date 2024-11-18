package be.ifapme.election.dto;

import be.ifapme.election.model.Partit;
import lombok.Data;

@Data
public class CandidatDto {
    private CandidatIdDto id;
    private PersonDto personne;
    private ElectionDto election;
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
