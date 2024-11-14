package be.ifapme.election.controller;

import be.ifapme.election.Exception.BusinessException;
import be.ifapme.election.command.CreatePersonCommand;
import be.ifapme.election.dto.PersonDto;
import be.ifapme.election.service.PersonService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
