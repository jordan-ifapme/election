package be.ifapme.election.Exception;

public class NotFoundCodeCountryException extends BusinessException{
    public NotFoundCodeCountryException() {
        super("Le pays n'a pas été trouvé.");
    }
}
