package be.ifapme.election.Exception;
public class invalidNationalRegisterException extends BusinessException {
    public invalidNationalRegisterException() {
        super("Le numéro de registre national ne fait pas 8 caractères de long");
    }
}