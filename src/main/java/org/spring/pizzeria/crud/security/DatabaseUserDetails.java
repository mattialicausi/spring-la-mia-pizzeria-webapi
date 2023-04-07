package org.spring.pizzeria.crud.security;

import org.spring.pizzeria.crud.model.Role;
import org.spring.pizzeria.crud.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DatabaseUserDetails implements UserDetails {

    //variabili
    private String username;
    private String password;

    private Set<GrantedAuthority> authorities;

    //costruttore personalizzato

    //passo alle variabili il valore dei cambi del Model user
    //creo un nuovo set e lo popolo per i ruoli che ha il nuovo utente
    public DatabaseUserDetails(User user) {
        this.username = user.getEmail();
        this.password = user.getPassword();

        this.authorities = new HashSet<>();

        for (Role r : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(r.getName()));
        }

    }

    //metodi implementati
    //sostituisco i return con le variabili create, cambio a true tutti i metodi che tornano un valore booleano
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
