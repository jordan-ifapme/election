package be.ifapme.election.service;

import be.ifapme.election.command.CreateAdresseCommand;
import be.ifapme.election.dto.AdresseDto;

import java.util.List;

public interface AdresseService {

    AdresseDto createAdresse(CreateAdresseCommand command);

    // generation id adresse
    Integer getRandomAdresse();

    List<AdresseDto> generateRandomAdresses(int nbrs);
}
