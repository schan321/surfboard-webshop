package com.saltyplank.webshop.controllers;

import com.saltyplank.webshop.config.CredentialValidator;
import com.saltyplank.webshop.config.JWTUtil;
import com.saltyplank.webshop.dto.request.LoginRequest;
import com.saltyplank.webshop.dto.request.RegisterRequest;
import com.saltyplank.webshop.dto.response.AuthResponse;
import com.saltyplank.webshop.enums.Role;
import com.saltyplank.webshop.models.Gebruiker;
import com.saltyplank.webshop.repository.GebruikerRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
public class AuthController {

    private final GebruikerRepository gebruikerRepository;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final CredentialValidator credentialValidator;

    public AuthController(GebruikerRepository gebruikerRepository, JWTUtil jwtUtil,
                          AuthenticationManager authManager, PasswordEncoder passwordEncoder,
                          CredentialValidator credentialValidator) {
        this.gebruikerRepository = gebruikerRepository;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
        this.passwordEncoder = passwordEncoder;
        this.credentialValidator = credentialValidator;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        if (!credentialValidator.isValidEmail(request.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid email format"
            );
        }

        if (!credentialValidator.isValidPassword(request.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Password must be 8-100 characters and contain uppercase, lowercase, number and special character"
            );
        }

        if (gebruikerRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email already in use"
            );
        }

        Gebruiker gebruiker = new Gebruiker();
        gebruiker.setFirstName(request.getFirstName());
        gebruiker.setLastName(request.getLastName());
        gebruiker.setEmail(request.getEmail());
        gebruiker.setPassword(passwordEncoder.encode(request.getPassword()));
        gebruiker.setRole(Role.USER);

        gebruikerRepository.save(gebruiker);

        String token = jwtUtil.generateToken(gebruiker.getEmail());
        return ResponseEntity.ok(new AuthResponse(token, gebruiker.getRole().name()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            Gebruiker gebruiker = gebruikerRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

            String token = jwtUtil.generateToken(gebruiker.getEmail());
            return ResponseEntity.ok(new AuthResponse(token, gebruiker.getRole().name()));

        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid credentials");
        }
    }
}