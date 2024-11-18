package be.ifapme.election.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vote")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vote {
    @EmbeddedId
    private VoteId id;

    @MapsId("personneId")
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "personne_id", nullable = false)
    private Personne personne;

    @MapsId("electionId")
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "election_id", nullable = false)
    private Election election;

}
