package be.ifapme.election.service.impl;

import be.ifapme.election.Exception.BusinessException;
import be.ifapme.election.Exception.NotFoundCodeCountryException;
import be.ifapme.election.command.CreateElectionCommand;
import be.ifapme.election.dto.ElectionDto;
import be.ifapme.election.model.Election;
import be.ifapme.election.model.Pays;
import be.ifapme.election.repository.ElectionRepository;
import be.ifapme.election.service.ElectionService;
import be.ifapme.election.utils.ModelMapperUtils;
import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ElectionServiceImpl implements ElectionService {
    private final ElectionRepository electionRepository;
    private final CountryServiceImpl countryService;
    private final ConvocationServiceImpl convocationService;


    public ElectionServiceImpl(ElectionRepository electionRepository, CountryServiceImpl countryService, ConvocationServiceImpl convocationService) {

        this.electionRepository = electionRepository;
        this.countryService = countryService;
        this.convocationService = convocationService;
    }

    @Override
    public Election findById(Integer id) {
        return electionRepository.findById(id).orElse(null);
    }

    @Override
    public ElectionDto create(CreateElectionCommand command) throws BusinessException, DocumentException, IOException {
        Pays pays = countryService.createPays(command.getCodePays());
        Election election = Election.builder()
                .nom(command.getNom())
                .dateLimite(command.getDateLimite())
                .codePays(pays.getCodePays())
                .build();
        convocationService.createConvocation(election);
        Election createdElection = electionRepository.save(election);
        return ModelMapperUtils
                .getInstance()
                .map(createdElection, ElectionDto.class);
    }
}
