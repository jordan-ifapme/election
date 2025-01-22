package be.ifapme.election.service.impl;

import be.ifapme.election.Exception.*;
import be.ifapme.election.command.CreateCandidatCommand;
import be.ifapme.election.dto.CandidatDto;
import be.ifapme.election.model.*;
import be.ifapme.election.repository.CandidatRepository;
import be.ifapme.election.repository.ElectionRepository;
import be.ifapme.election.repository.PartiRepository;
import be.ifapme.election.repository.PersonRepository;
import be.ifapme.election.service.CandidatService;
import be.ifapme.election.utils.ModelMapperUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class CandidatServiceImpl implements CandidatService {
    private final PersonRepository personRepository;
    private final ElectionRepository electionRepository;
    private final PartiRepository partiRepository;
    private final CandidatRepository candidatRepository;

    public CandidatServiceImpl(PersonRepository personRepository, ElectionRepository electionRepository, PartiRepository partiRepository, CandidatRepository candidatRepository) {
        this.personRepository = personRepository;
        this.electionRepository = electionRepository;
        this.partiRepository = partiRepository;
        this.candidatRepository = candidatRepository;
    }

    @Override
    public CandidatDto create(CreateCandidatCommand command) throws BusinessException {
        Personne personne = personRepository.findById(command.getPersonneId()).orElse(null);
        if (personne == null) {
            throw new PersonneNotFoundException(command.getPersonneId());
        }

        verifyAge(personne);

        Election election = electionRepository.findById(command.getElectionId()).orElse(null);
        if (election == null) {
            throw new ElectionNotFoundException(command.getElectionId());
        }
        Partit partit = partiRepository.findById(command.getPartitId()).orElse(null);
        if (partit == null) {
            throw new PartitNotFoundException(command.getPartitId());
        }

        if (candidatRepository.findByPersonneIdAndElectionId(command.getPersonneId(), command.getElectionId()).isPresent()) {
            throw new CandidatAlreadyExistException();
        }

        CandidatId candidatId = CandidatId.builder().electionId(election.getId()).personneId(personne.getId()).build();

        Candidat candidat = Candidat.builder()
                .partit(partit)
                .id(candidatId)
                .election(election)
                .personne(personne)
                .vote(0)
                .build();
        Candidat createCandidat = candidatRepository.save(candidat);
        return ModelMapperUtils.getInstance().map(createCandidat, CandidatDto.class);
    }
    private void verifyAge(Personne personne) throws BusinessException {
        LocalDate today = LocalDate.now();
        LocalDate birthDate = personne.getBirthDate();
        int age = Period.between(birthDate, today).getYears();

        if (age < 18) {
            throw new AgeException();
        }
    }
}
