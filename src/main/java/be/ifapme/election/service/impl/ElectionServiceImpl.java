package be.ifapme.election.service.impl;

import be.ifapme.election.command.CreateElectionCommand;
import be.ifapme.election.dto.ElectionDto;
import be.ifapme.election.model.Election;
import be.ifapme.election.repository.ElectionRepository;
import be.ifapme.election.service.ElectionService;
import be.ifapme.election.utils.ModelMapperUtils;
import org.springframework.stereotype.Service;

@Service
public class ElectionServiceImpl implements ElectionService {
    private final ElectionRepository electionRepository;


    public ElectionServiceImpl( ElectionRepository electionRepository) {

        this.electionRepository = electionRepository;
    }

    @Override
    public Election findById(Integer id){
        return electionRepository.findById(id).orElse(null);
    }

    @Override
    public ElectionDto create(CreateElectionCommand command) {
        Election election = Election.builder()
                .nom(command.getNom())
                .date_limite(command.getDate_limite())
                .build();
        Election createdElection = electionRepository.save(election);
        return ModelMapperUtils
                .getInstance()
                .map(createdElection, ElectionDto.class);
    }
}
