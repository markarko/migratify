package com.markarko.migratify.controllers;

import com.markarko.migratify.auth.AccessTokenResponse;

import com.markarko.migratify.services.AuthService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public ResponseEntity<String> login() {
        return new ResponseEntity<>(authService.buildLoginHeaders(), HttpStatus.FOUND);
    }
    @GetMapping("/")
    public ResponseEntity accessToken(@RequestParam("code") String code, @RequestParam("state") String state) {
        if (state == null) {
            return new ResponseEntity<>("Authentication failed. State mismatch.", HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<AccessTokenResponse> response = authService.buildAccessTokenResponse(code);

        if (!response.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<>("Authentication failed. Please try again.", response.getStatusCode());
        }

        AccessTokenResponse accessTokenResponse = response.getBody();
        accessTokenResponse.getAccessToken();
        return new ResponseEntity<>("Authentication successful, you can now close this window.", HttpStatus.OK);
    }
}
