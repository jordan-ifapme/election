package be.ifapme.election.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "candidat")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Candidat {
    @EmbeddedId
    private CandidatId id;

    @MapsId("personneId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "personne_id", nullable = false)
    private Personne personne;

    @MapsId("electionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "election_id", nullable = false)
    private Election election;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partit_id")
    private Partit partit;

    @ColumnDefault("0")
    @Column(name = "vote")
    private Integer vote;

}
