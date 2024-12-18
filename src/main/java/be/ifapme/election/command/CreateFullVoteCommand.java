package be.ifapme.election.command;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class CreateFullVoteCommand {
    private Integer personneId;
    private Integer candidatElectionId;
    private Integer candidatPersonId;
    private LocalDateTime dateVote;
}
