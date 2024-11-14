package be.ifapme.election.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidatId implements Serializable {
    private static final long serialVersionUID = -4113268659772947585L;
    @Column(name = "personne_id", nullable = false)
    private Integer personneId;

    @Column(name = "election_id", nullable = false)
    private Integer electionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CandidatId entity = (CandidatId) o;
        return Objects.equals(this.electionId, entity.electionId) &&
                Objects.equals(this.personneId, entity.personneId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(electionId, personneId);
    }

}
