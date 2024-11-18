package be.ifapme.election.service.impl;

import be.ifapme.election.command.CreateVoteCommand;
import be.ifapme.election.dto.VoteDto;
import be.ifapme.election.model.Election;
import be.ifapme.election.model.Personne;
import be.ifapme.election.model.Vote;
import be.ifapme.election.model.VoteId;
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



    public VoteServiceImpl(VoteRepository voteRepository, PersonService personService, ElectionService electionService) {
        this.voteRepository = voteRepository;
        this.personService = personService;
        this.electionService = electionService;
    }


    @Override
    public VoteDto createvote(CreateVoteCommand command) {
        Personne personne = personService.findById(command.getPersonneId());
        Election election = electionService.findById(command.getElectionId());

        VoteId voteId = new VoteId();
        voteId.setPersonneId(command.getPersonneId());
        voteId.setElectionId(command.getElectionId());

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
