package be.ifapme.election.Exception;

public class AlreadyVotedException extends BusinessException{
  public AlreadyVotedException(String nom, String prenom) {
    super("L'utilisateur " + nom + " " + prenom + " a déjà voté pour cette élection");
  }
}
