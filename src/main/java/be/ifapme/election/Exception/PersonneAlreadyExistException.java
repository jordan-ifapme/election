package be.ifapme.election.Exception;

public class PersonneAlreadyExistException extends BusinessException {
    public PersonneAlreadyExistException() {
        super("la personne existe déjà.");
    }
}