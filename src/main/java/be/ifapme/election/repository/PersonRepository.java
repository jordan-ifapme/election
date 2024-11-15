package be.ifapme.election.repository;

import be.ifapme.election.model.Adresse;
import be.ifapme.election.model.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Personne, Integer> {
}
