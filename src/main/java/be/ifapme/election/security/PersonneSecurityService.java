package be.ifapme.election.security;

import be.ifapme.election.model.Personne;
import be.ifapme.election.repository.PersonRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PersonneSecurityService implements UserDetailsService {
    private final PersonRepository personneRepository;

    public PersonneSecurityService(PersonRepository personneRepository) {
        this.personneRepository = personneRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String numeroNational) throws UsernameNotFoundException {
        Personne personne = personneRepository.findByRegistreNational(numeroNational)
                .orElseThrow(() -> new UsernameNotFoundException(numeroNational));

        String role = "USER";

        if(personne.getIs_admin()) {
            role = "ADMIN";
        }

        return new User(
                personne.getRegistreNational(),
                personne.getPassword(),
                List.of(new SimpleGrantedAuthority(role)));

    }
}
