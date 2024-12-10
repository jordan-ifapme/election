package be.ifapme.election.repository;

import be.ifapme.election.model.Pays;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository <Pays, String> {
    Optional<Pays> findPaysByCodePaysIgnoreCase(String codePays);
}
