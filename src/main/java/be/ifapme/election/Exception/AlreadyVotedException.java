package be.ifapme.election.Exception;

public class AlreadyVotedException extends BusinessException {
    public AlreadyVotedException() {

        super("Cette personne a déjà voté pour cette élection !");
    }
}
