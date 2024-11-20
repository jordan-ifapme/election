package be.ifapme.election.service;

import be.ifapme.election.Exception.BusinessException;
import be.ifapme.election.command.CreateVoteCommand;
import be.ifapme.election.dto.VoteDto;
import org.springframework.http.ResponseEntity;

public interface VoteService {
    VoteDto createvote(CreateVoteCommand command) throws BusinessException;
    ResponseEntity<String> votejson () throws BusinessException;
}
