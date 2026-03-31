package com.saltyplank.webshop.services;

import com.saltyplank.webshop.repository.GebruikerRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private final GebruikerRepository gebruikerRepository;

    public UserService(GebruikerRepository gebruikerRepository) {
        this.gebruikerRepository = gebruikerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return gebruikerRepository.findByEmail(email)
                .map(gebruiker -> new User(
                        gebruiker.getEmail(),
                        gebruiker.getPassword(),
                        Collections.singletonList(
                                new SimpleGrantedAuthority("ROLE_" + gebruiker.getRole().name())
                        )
                ))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
