package be.ifapme.election.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "candidat")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Candidat {
    @EmbeddedId
    private CandidatId id;

    @MapsId("personneId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personne_id")
    private Personne personne;

    @MapsId("electionId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "election_id")
    private Election election;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partit_id")
    private Partit partit;

    @Column(name = "vote")
    private Integer vote = 0;

}
