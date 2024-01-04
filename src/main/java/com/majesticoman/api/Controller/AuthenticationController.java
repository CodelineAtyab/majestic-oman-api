package com.majesticoman.api.Controller;

import com.majesticoman.api.Models.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/api/v1/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            if (authenticate.isAuthenticated()) {
                return ResponseEntity.ok("Authenticated successfully");
            } else {
                return ResponseEntity.status(401).body("Authentication failed");
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Authentication failed");
        }
    }
}

