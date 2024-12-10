package be.ifapme.election.Exception;

import java.time.LocalDateTime;

public class ElectionFinishedException extends BusinessException {
    public ElectionFinishedException(Integer id, LocalDateTime dateLimite) {

      super("L'élection avec l'id " + id + " est terminée depuis :" + dateLimite);
    }
}
