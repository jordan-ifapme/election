package be.ifapme.election.Exception;

public class PersonneNotFoundException extends BusinessException {
    public PersonneNotFoundException(Integer id) {
        super("La personne avec l'id " + id + " n'existe pas");
    }
}