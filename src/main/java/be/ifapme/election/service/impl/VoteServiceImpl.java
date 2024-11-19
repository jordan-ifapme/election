package be.ifapme.election.service.impl;

import be.ifapme.election.Exception.AlreadyVotedException;
import be.ifapme.election.Exception.BusinessException;
import be.ifapme.election.Exception.CandidatNotFoundException;
import be.ifapme.election.command.CreateVoteCommand;
import be.ifapme.election.dto.VoteDto;
import be.ifapme.election.model.*;
import be.ifapme.election.repository.CandidatRepository;
import be.ifapme.election.repository.VoteRepository;
import be.ifapme.election.service.ElectionService;
import be.ifapme.election.service.PersonService;
import be.ifapme.election.service.VoteService;
import be.ifapme.election.utils.ModelMapperUtils;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final PersonService personService;
    private final ElectionService electionService;
    private final CandidatRepository candidatRepository;



    public VoteServiceImpl(VoteRepository voteRepository, PersonService personService, ElectionService electionService, CandidatRepository candidatRepository) {
        this.voteRepository = voteRepository;
        this.personService = personService;
        this.electionService = electionService;
        this.candidatRepository = candidatRepository;
    }


    @Override
    public VoteDto createvote(CreateVoteCommand command) throws BusinessException {
        Personne personne = personService.findById(command.getPersonneId());
        Election election = electionService.findById(command.getElectionId());

        VoteId voteId = new VoteId();
        voteId.setPersonneId(command.getPersonneId());
        voteId.setElectionId(command.getElectionId());

        CandidatId candidatId = new CandidatId();
        candidatId.setElectionId(command.getElectionId());
        candidatId.setPersonneId(command.getCandidatId());

        Candidat aEteVote = candidatRepository.findById(candidatId).orElse(null);
        if (aEteVote == null) {
            throw new CandidatNotFoundException(candidatId.getPersonneId());
        }


        Vote aDejaVote = voteRepository.findById(voteId).orElse(null);

        if(aDejaVote != null){
            throw new AlreadyVotedException();
        }

        aEteVote.setVote(aEteVote.getVote() + 1);
        candidatRepository.save(aEteVote);

        Vote createvote = Vote.builder()
                .id(voteId)
                .personne(personne)
                .election(election).build();



        Vote savedVote = voteRepository.save(createvote);

        return ModelMapperUtils
                .getInstance()
                .map(savedVote, VoteDto.class);
    }

}
