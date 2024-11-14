package be.ifapme.election.repository;

import be.ifapme.election.model.Partit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartiRepository extends JpaRepository<Partit, Integer> {
}
