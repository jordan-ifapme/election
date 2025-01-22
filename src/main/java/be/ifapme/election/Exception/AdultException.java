package be.ifapme.election.Exception;

public class AdultException extends BusinessException{
    public AdultException(){
        super("La personne doit avoir 18 ans ou plus.");
    }
}
