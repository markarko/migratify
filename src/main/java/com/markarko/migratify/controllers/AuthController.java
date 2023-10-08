package com.markarko.migratify.controllers;

import com.markarko.migratify.auth.AccessTokenResponse;
import com.markarko.migratify.auth.Auth;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final Auth auth;

    public AuthController(Auth auth) {
        this.auth = auth;
    }

    @GetMapping("/login")
    public ResponseEntity<String> login() {
        return new ResponseEntity<>(auth.buildLoginHeaders(), HttpStatus.FOUND);
    }
    @GetMapping("/")
    public ResponseEntity accessToken(@RequestParam("code") String code, @RequestParam("state") String state) {
        if (state == null) {
            return new ResponseEntity<>("Authentication failed. State mismatch.", HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<AccessTokenResponse> response = auth.buildAccessTokenResponse(code);

        if (!response.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<>("Authentication failed. Please try again.", response.getStatusCode());
        }

        // auth successful
        AccessTokenResponse accessTokenResponse = response.getBody();
        return new ResponseEntity<>("Access token: " + accessTokenResponse.getAccessToken(), HttpStatus.OK);
    }
}
