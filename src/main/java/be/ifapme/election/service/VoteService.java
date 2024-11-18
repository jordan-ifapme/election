package be.ifapme.election.service;

import be.ifapme.election.Exception.AlreadyVotedException;
import be.ifapme.election.Exception.BusinessException;
import be.ifapme.election.command.CreateVoteCommand;
import be.ifapme.election.dto.VoteDto;

public interface VoteService {
    VoteDto createvote(CreateVoteCommand command) throws BusinessException;
}
