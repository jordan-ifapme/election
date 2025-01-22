package be.ifapme.election.Exception;

public class PersonneNotMajorException extends BusinessException {
    public PersonneNotMajorException() {
        super("La personne n'est pas majeure");
    }
}