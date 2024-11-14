package be.ifapme.election.Exception;

public class ElectionNotFoundException extends BusinessException {
    public ElectionNotFoundException(Integer id) {

        super("Le parti avec l'id " + id + " n'existe pas");
    }
}
