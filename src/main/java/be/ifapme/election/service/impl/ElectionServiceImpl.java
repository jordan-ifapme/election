package be.ifapme.election.service.impl;

import be.ifapme.election.command.CreateElectionCommand;
import be.ifapme.election.dto.ElectionDto;
import be.ifapme.election.model.Election;
import be.ifapme.election.repository.ElectionRepository;
import be.ifapme.election.service.ConvactionService;
import be.ifapme.election.service.ElectionService;

import be.ifapme.election.utils.ModelMapperUtils;
import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class ElectionServiceImpl implements ElectionService {
    private final ElectionRepository electionRepository;
    private final ConvactionService convactionService;

    public ElectionServiceImpl(ElectionRepository electionRepository, ConvactionService convactionService) {

        this.electionRepository = electionRepository;
        this.convactionService = convactionService;
    }

    @Override
    public Election findById(Integer id) {
        return electionRepository.findById(id).orElse(null);
    }

    @Override
    public ElectionDto create(CreateElectionCommand command) throws DocumentException, IOException {
        Election election = Election.builder()
                .nom(command.getNom())
                .build();
        Election createdElection = electionRepository.save(election);
        convactionService.createConvocation(election);
        return ModelMapperUtils
                .getInstance()
                .map(createdElection, ElectionDto.class);
    }
}
