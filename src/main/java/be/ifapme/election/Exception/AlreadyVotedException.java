package be.ifapme.election.Exception;

public class AlreadyVotedException extends BusinessException{
  public AlreadyVotedException() {
    super("t'as deja voté");
  }
}
