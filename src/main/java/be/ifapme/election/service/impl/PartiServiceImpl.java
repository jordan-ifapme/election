package be.ifapme.election.service.impl;

import be.ifapme.election.command.CreatePartiCommand;
import be.ifapme.election.dto.PartiDto;
import be.ifapme.election.model.Partit;
import be.ifapme.election.repository.PartiRepository;
import be.ifapme.election.service.PartiService;
import be.ifapme.election.utils.ModelMapperUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PartiServiceImpl implements PartiService {

    private PartiRepository partiRepository;


    public PartiServiceImpl(PartiRepository partiRepository) {
        this.partiRepository = partiRepository;

    }
    @Override
    public PartiDto createParti(CreatePartiCommand command) {
        Partit partit = Partit.builder()
                .nom(command.getNom())
                .couleur(command.getCouleur()).build();

        Partit createParti = partiRepository.save(partit);

        return ModelMapperUtils
                .getInstance()
                .map(createParti, PartiDto.class);
    }
}
