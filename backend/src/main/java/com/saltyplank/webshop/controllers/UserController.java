package com.saltyplank.webshop.controllers;

import com.saltyplank.webshop.dto.request.RegisterRequest;
import com.saltyplank.webshop.dto.response.UserDTO;
import com.saltyplank.webshop.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getProfile(Principal principal) {
        return ResponseEntity.ok(userService.getProfile(principal.getName()));
    }

    @PutMapping("/me")
    public ResponseEntity<UserDTO> updateProfile(Principal principal,
                                                 @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.updateProfile(principal.getName(), request));
    }
}