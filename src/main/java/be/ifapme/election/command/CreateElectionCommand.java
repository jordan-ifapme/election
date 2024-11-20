package be.ifapme.election.command;

import lombok.Getter;
import java.time.LocalDateTime;


@Getter
public class CreateElectionCommand {
    String nom;
    LocalDateTime date_limite;
}
