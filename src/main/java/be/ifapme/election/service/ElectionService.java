package be.ifapme.election.service;

import be.ifapme.election.command.CreateElectionCommand;
import be.ifapme.election.dto.ElectionDto;

public interface ElectionService {
    ElectionDto create(CreateElectionCommand command);
}
