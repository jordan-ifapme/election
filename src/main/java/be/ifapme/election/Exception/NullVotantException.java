package be.ifapme.election.Exception;

public class NullVotantException extends BusinessException {
    public NullVotantException() {
        super("Le votant est null");
    }
}
