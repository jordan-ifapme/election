package be.ifapme.election.controller.rest;

import be.ifapme.election.Exception.BusinessException;
import be.ifapme.election.command.CreateVoteCommand;
import be.ifapme.election.dto.VoteDto;
import be.ifapme.election.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vote")
public class VoteController {
    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    public VoteDto vote(@RequestBody CreateVoteCommand command) throws BusinessException {
        return voteService.createvote(command);
    }


    @GetMapping("/votesjson")
    public ResponseEntity<String> votesjson() throws BusinessException {
        return voteService.votejson();
    }

}
