package be.ifapme.election.repository;


import be.ifapme.election.model.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Personne, Integer> {
    Optional<Personne> findByRegistreNational(String registreNational);
    List<Personne> findAllByAdresse_CodePaysIgnoreCase(String code);
}
