package be.ifapme.election.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "candidat")
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "partit_id" , nullable = false)
    private Partit partit;

    @ColumnDefault("0")
    @Column(name = "vote")
    private Integer vote;

}