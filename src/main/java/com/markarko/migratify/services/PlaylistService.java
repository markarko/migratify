package com.markarko.migratify.services;

import com.markarko.migratify.entities.Playlist;
import com.markarko.migratify.entities.Playlists;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaylistService {

    private Environment env;

    public PlaylistService(Environment env) {
        this.env = env;
    }

    public List<Playlist> getPlaylists(String accessToken) throws HttpClientErrorException {
        String userId = env.getProperty("user_id");
        String url = "https://api.spotify.com/v1/users/" + userId + "/playlists";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Playlists> response = restTemplate.exchange(url, HttpMethod.GET, request, Playlists.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody().getPlaylists();
        }

        // An HttpClientErrorException will be thrown if the playlists couldn't be fetched
        return new ArrayList<>();
    }
}
