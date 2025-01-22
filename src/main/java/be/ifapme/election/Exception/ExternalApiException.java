package be.ifapme.election.Exception;

public class ExternalApiException extends BusinessException {
    public ExternalApiException() {
        super("Erreur lors de l'appel à l'API externe");
    }
}