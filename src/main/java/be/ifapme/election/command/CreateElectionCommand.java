package be.ifapme.election.command;

import lombok.Getter;
import java.time.LocalDateTime;


@Getter
public class CreateElectionCommand {
    private String nom;
    private LocalDateTime dateLimite;
    private String codePays;
}
