package be.ifapme.election.Exception;

public class PersonneFeignClientNotFoundException extends BusinessException {
    public PersonneFeignClientNotFoundException() {
        super("Désolé, aucune personne n'a été trouvée");
    }
}