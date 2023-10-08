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
}
