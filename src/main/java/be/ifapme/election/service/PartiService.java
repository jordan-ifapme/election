package be.ifapme.election.service;

import be.ifapme.election.command.CreatePartiCommand;
import be.ifapme.election.dto.PartiDto;

public interface PartiService {
    PartiDto createParti(CreatePartiCommand command);
}
