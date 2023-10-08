package com.markarko.migratify.auth;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Base64;
import java.util.Random;

@Component
public class Auth {
    private final Environment env;

    public Auth(Environment env) {
        this.env = env;
    }

    public HttpHeaders buildLoginHeaders() {
        String url = buildLoginUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", url);
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        return headers;
    }
    private String buildLoginUrl() {
        String state = generateRandomString(16);
        String scope = "user-read-private user-read-email";
        String authUrl = "https://accounts.spotify.com/authorize?";
        String redirectUri = env.getProperty("redirect_uri");;
        String clientId = env.getProperty("client_id");;

        return UriComponentsBuilder.fromHttpUrl(authUrl)
                .queryParam("response_type", "code")
                .queryParam("client_id", clientId)
                .queryParam("scope", scope)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("state", state)
                .toUriString();
    }
    private String generateRandomString(int length) {
        Random rand = new Random();

        String text = "";
        String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < length; i++) {
            text += possible.charAt(rand.nextInt(possible.length()));
        }

        return text;
    }
}
