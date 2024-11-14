package be.ifapme.election.Exception;

public class AdressNotFoundException extends BusinessException{
    public AdressNotFoundException(){
        super("L'adresse n'existe pas !");
    }
}
