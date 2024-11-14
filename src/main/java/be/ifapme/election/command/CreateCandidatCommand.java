package be.ifapme.election.command;

import lombok.Getter;

@Getter
public class CreateCandidatCommand {
    private Integer personneId;
    private Integer electionId;
    private Integer partitId;
}
