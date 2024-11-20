package be.ifapme.election.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VoteJson {
    @JsonProperty("personne_id")
    private Integer personneId;

    @JsonProperty("election_id")
    private Integer electionId;

    @JsonProperty("candidat_id")
    private Integer candidatId;
}
