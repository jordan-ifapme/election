package be.ifapme.election.Exception;

public class CandidatAlreadyExistException extends BusinessException {
    public CandidatAlreadyExistException() {

        super("Ce candidat existe déjà !");
    }

}
