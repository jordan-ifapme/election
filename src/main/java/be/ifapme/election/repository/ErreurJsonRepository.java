package be.ifapme.election.repository;

import be.ifapme.election.model.ErreurJson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErreurJsonRepository  extends JpaRepository<ErreurJson,Integer> {
}
