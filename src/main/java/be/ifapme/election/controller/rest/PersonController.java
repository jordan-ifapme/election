package be.ifapme.election.controller.rest;

import be.ifapme.election.Exception.BusinessException;
import be.ifapme.election.command.CreatePersonCommand;
import be.ifapme.election.dto.PersonDto;
import be.ifapme.election.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @PostMapping
    public PersonDto createPerson(@RequestBody CreatePersonCommand person) throws BusinessException {
        return personService.createPerson(person);
    }

    @PutMapping("/{id}")
    public String createPerson(@PathVariable int id) throws BusinessException {
        return personService.modifyPerson(id);
    }

    @PostMapping("/generate/{nbrs}")
    public List<PersonDto> generateRandomPersons(@PathVariable int nbrs) throws BusinessException {
        return personService.generateRandomPersons(nbrs);
    }

}
