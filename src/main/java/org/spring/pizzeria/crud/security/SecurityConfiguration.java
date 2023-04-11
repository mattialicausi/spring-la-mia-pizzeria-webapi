package org.spring.pizzeria.crud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    //impprto un metodo che restituisce un nuovo DTUserService
    @Bean
    UserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    //metodo che restiruisce un oggetto per criptare e decrittare le password
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //metodo che mette insieme UserDetailsService e passwordEncoder per eseguire le query per autenticare gli utenti
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        // creo un authentication provider basato su database (DAO)
        provider.setUserDetailsService(userDetailsService());

        // associo il mio DatabaseUserDetailsService
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    /*
    * home "/" USER, ADMIN
    * pizza "/pizza" USER, ADMIN
    * edit "/pizza/edit/{id}" ADMIN *****************************
    * create "/pizza/create/{id}" ADMIN *************************
    * delete "/pizza/delete/{id} ADMIN **************************
    * show "pizza/{id} USER, ADMIN
    * offer "/offer/...." ADMIN *********************************
    * ingredient "/ingredient/..." ADMIN ************************
    * */

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/ingredient", "/ingredient/**").hasAuthority("ADMIN")
                .requestMatchers("/offer/**").hasAuthority("ADMIN")
                .requestMatchers("/pizza/edit/**", "/pizza/create", "/pizza/delete/**").hasAuthority("ADMIN")
                .requestMatchers("/pizza", "/pizza/**", "/").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/pizza/**").hasAuthority("ADMIN")
                .requestMatchers("/**").permitAll()
                .and().formLogin()
                .and().logout()
                .and().exceptionHandling();
        http.csrf().disable();
        return http.build();
    }

}
