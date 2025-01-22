package be.ifapme.election.Exception;

public class InvalidNationalRegistrationLengthExceptionException extends BusinessException{
    public InvalidNationalRegistrationLengthExceptionException(){
        super("Le registre national doit contenir exactement 8 caractères.");
    }
}
