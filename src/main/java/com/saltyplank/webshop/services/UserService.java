package com.saltyplank.webshop.services;

import com.saltyplank.webshop.dto.request.RegisterRequest;
import com.saltyplank.webshop.dto.response.GebruikerDTO;
import com.saltyplank.webshop.models.Gebruiker;
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

    public GebruikerDTO getProfile(String email) {
        Gebruiker gebruiker = gebruikerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new GebruikerDTO(
                gebruiker.getId(),
                gebruiker.getFirstName(),
                gebruiker.getLastName(),
                gebruiker.getEmail(),
                gebruiker.getRole().name()
        );
    }

    public GebruikerDTO updateProfile(String email, RegisterRequest request) {
        Gebruiker gebruiker = gebruikerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        gebruiker.setFirstName(request.getFirstName());
        gebruiker.setLastName(request.getLastName());

        gebruikerRepository.save(gebruiker);

        return new GebruikerDTO(
                gebruiker.getId(),
                gebruiker.getFirstName(),
                gebruiker.getLastName(),
                gebruiker.getEmail(),
                gebruiker.getRole().name()
        );
    }
}