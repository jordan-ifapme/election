package be.ifapme.election.command;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CreateElectionCommand {
    private String nom;
    private LocalDateTime dateLimite;
}
