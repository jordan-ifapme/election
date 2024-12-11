package be.ifapme.election.repository;

import be.ifapme.election.model.Candidat;
import be.ifapme.election.model.CandidatId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidatRepository extends JpaRepository<Candidat, CandidatId> {
    List<Candidat> findAllByElection_Id (Integer electionId);
    Optional<Candidat> findByPersonneIdAndElectionId(Integer personneId, Integer electionId);
}
