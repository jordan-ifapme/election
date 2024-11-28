package be.ifapme.election.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCandidatCommand {
    private Integer personneId;
    private Integer electionId;
    private Integer partitId;
}
