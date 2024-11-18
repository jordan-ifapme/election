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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personne_id")
    private Personne personne;

    @MapsId("electionId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "election_id")
    private Election election;

}
