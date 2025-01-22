package be.ifapme.election.Exception;

public class ErrorNISSException extends BusinessException{
    public ErrorNISSException(){
        super("Pas de personne pour le NISS.");
    }
}
