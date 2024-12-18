package be.ifapme.election.command;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class CreateElectionCommand {
    private String nom;
    private LocalDateTime dateLimite;
    private String codePays;
}
