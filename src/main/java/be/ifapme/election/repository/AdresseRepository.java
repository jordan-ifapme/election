package be.ifapme.election.repository;

import be.ifapme.election.model.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdresseRepository extends JpaRepository<Adresse, Integer> {
    // Méthode pour récupérer tous les IDs des adresses
    @Query("SELECT a.id FROM Adresse a")
    List<Integer> findAllAdresseIds();

}
