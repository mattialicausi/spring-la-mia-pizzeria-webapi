package org.spring.pizzeria.crud.security;

import org.spring.pizzeria.crud.model.User;
import org.spring.pizzeria.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    //creo un instanza di userRepository per usare una query custom per prendere dal DB l'utente con quell'username
    @Autowired
    private UserRepository userRepository;

    //ovveride di un metodo in cui salvo l'username e se è presente ritorno un nuovo DatabaseuserDetails sennò lancio un eccezione
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(username);

        if (user.isPresent()) {
            return new DatabaseUserDetails(user.get());
        } else {
            throw new UsernameNotFoundException("User con email " + username + "non trovato");
        }

    }


}
