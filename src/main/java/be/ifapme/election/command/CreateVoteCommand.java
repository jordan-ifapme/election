package be.ifapme.election.command;

import lombok.Data;

@Data
public class CreateVoteCommand {
    private Integer candidatElectionId;
    private Integer candidatPersonId;
}
