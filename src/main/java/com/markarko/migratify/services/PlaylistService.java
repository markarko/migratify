package com.markarko.migratify.services;

import com.markarko.migratify.entities.Playlist;
import com.markarko.migratify.entities.Playlists;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlaylistService {

    private Environment env;

    public PlaylistService(Environment env) {
        this.env = env;
    }

    public List<Playlist> getPlaylists(String accessToken) throws HttpClientErrorException {
        String userId = env.getProperty("user_id");
        String url = String.format("https://api.spotify.com/v1/users/%s/playlists", userId);

        HttpHeaders headers = buildHeaders(accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Playlists> response = restTemplate.exchange(url, HttpMethod.GET, request, Playlists.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody().getPlaylists();
        }

        return new ArrayList<>();
    }

    public String postPlaylist(String accessToken, String playlistName) throws HttpClientErrorException {
        String userId = env.getProperty("user_id");
        String url = String.format("https://api.spotify.com/v1/users/%s/playlists", userId);

        HttpHeaders headers = buildHeaders(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("name", playlistName);

        Map<String, String> data = new HashMap<>();
        data.put("name", playlistName);
        data.put("public", "false");

        HttpEntity<Map<String, String>> request = new HttpEntity<>(data, headers);
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(url, HttpMethod.POST, request, Playlist.class).getBody().getId();
    }

    private HttpHeaders buildHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Authorization", "Bearer " + accessToken);

        return headers;
    }

}
