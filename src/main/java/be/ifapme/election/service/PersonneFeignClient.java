package be.ifapme.election.service;

import be.ifapme.election.model.PersonneAPI;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "personneClient", url = "https://personne-express.onrender.com/api/person/")
public interface PersonneFeignClient {

    @GetMapping("/{code}")
    PersonneAPI getPersonneByRegistreNational(@PathVariable("code") String code);
}