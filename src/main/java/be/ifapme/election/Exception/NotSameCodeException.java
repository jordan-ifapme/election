package be.ifapme.election.Exception;

public class NotSameCodeException extends BusinessException {
    public NotSameCodeException() {
        super("Not same code");
    }
}
