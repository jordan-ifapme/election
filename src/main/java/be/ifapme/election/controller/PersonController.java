package be.ifapme.election.controller;

import be.ifapme.election.Exception.BusinessException;
import be.ifapme.election.command.CreatePersonCommand;
import be.ifapme.election.dto.PersonDto;
import be.ifapme.election.generator.PersonGenerator;
import be.ifapme.election.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/person")
public class PersonController {

    private final PersonService personService;
    private final PersonGenerator personGenerator;

    public PersonController(PersonService personService, PersonGenerator personGenerator) {
        this.personService = personService;
        this.personGenerator = personGenerator;
    }


    @PostMapping
    public PersonDto createPerson(@RequestBody CreatePersonCommand person) throws BusinessException {
        return personService.createPerson(person);
    }



    // generation de personne
    @PostMapping("/generate/{nbrs}")
    public List<PersonDto> generateRandomPersons(@PathVariable int nbrs) throws BusinessException {
        return personService.generateRandomPersons(nbrs);
    }


}
