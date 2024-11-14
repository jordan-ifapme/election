package be.ifapme.election.service;

import be.ifapme.election.Exception.BusinessException;
import be.ifapme.election.command.CreateCandidatCommand;
import be.ifapme.election.dto.CandidatDto;

public interface CandidatService {
    CandidatDto create(CreateCandidatCommand command) throws BusinessException;
}
