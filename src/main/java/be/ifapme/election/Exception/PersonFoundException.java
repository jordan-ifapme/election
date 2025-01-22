package be.ifapme.election.Exception;

public class PersonFoundException extends BusinessException{
    public PersonFoundException(){
        super("La personne existe déjà.");
    }
}
