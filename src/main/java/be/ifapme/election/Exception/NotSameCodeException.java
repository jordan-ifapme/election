package be.ifapme.election.Exception;

public class NotSameCodeException extends BusinessException {
    public NotSameCodeException() {
        super("Le pays ne correspond pas !");
    }
}
