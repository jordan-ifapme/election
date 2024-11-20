package be.ifapme.election.command;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
public class CreateVoteCommand {
    private Integer personneId;
    private Integer electionId;
    private Integer candidatId;
    private LocalDateTime dateVote;
}
