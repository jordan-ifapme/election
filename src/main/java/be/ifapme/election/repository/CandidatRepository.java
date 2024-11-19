package be.ifapme.election.repository;

import be.ifapme.election.model.Candidat;
import be.ifapme.election.model.CandidatId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatRepository extends JpaRepository<Candidat, CandidatId> {
}
