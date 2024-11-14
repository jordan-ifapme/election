package be.ifapme.election.Exception;

public class PartitNotFoundException extends BusinessException {
    public PartitNotFoundException(Integer id) {
        super("Le parti avec l'id " + id + " n'existe pas");
    }
}
