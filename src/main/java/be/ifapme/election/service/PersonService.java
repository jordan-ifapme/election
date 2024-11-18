package be.ifapme.election.service;

import be.ifapme.election.Exception.BusinessException;
import be.ifapme.election.command.CreatePersonCommand;
import be.ifapme.election.dto.PersonDto;

import java.util.List;

public interface PersonService {
    PersonDto createPerson(CreatePersonCommand person) throws BusinessException;

    List<PersonDto> generateRandomPersons(int nbrs) throws BusinessException;
}
