package be.ifapme.election.service;

import be.ifapme.election.Exception.NotFoundCodeCountryException;
import be.ifapme.election.command.CreateElectionCommand;
import be.ifapme.election.dto.ElectionDto;
import be.ifapme.election.model.Election;

public interface ElectionService {
    Election findById(Integer id);

    ElectionDto create(CreateElectionCommand command) throws NotFoundCodeCountryException;
}
