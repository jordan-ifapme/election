package be.ifapme.election.command;

import lombok.Getter;

@Getter
public class CreateVoteCommand {
    private Integer personneId;
    private Integer electionId;
    private Integer candidatId;
}
