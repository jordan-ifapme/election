package be.ifapme.election.Exception;

public class CandidatNotFoundException extends BusinessException {
    public CandidatNotFoundException(Integer id) {

        super("Le candidat avec l'id " + id + " n'existe pas");
    }
}
