package be.ifapme.election.service;

import be.ifapme.election.Exception.BusinessException;
import be.ifapme.election.command.CreateAdresseCommand;
import be.ifapme.election.dto.AdresseDto;

import java.util.List;

public interface AdresseService {

    AdresseDto createAdresse(CreateAdresseCommand command) throws BusinessException;

    Integer getRandomAdresseId();

    List<AdresseDto> generateRandomAdresses(int nbrs) throws BusinessException;
}
