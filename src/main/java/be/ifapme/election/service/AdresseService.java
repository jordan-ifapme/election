package be.ifapme.election.service;

import be.ifapme.election.command.CreateAdresseCommand;
import be.ifapme.election.dto.AdresseDto;

public interface AdresseService {

    AdresseDto createAdresse(CreateAdresseCommand command);
}
