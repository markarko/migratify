package com.markarko.migratify.services;

import com.markarko.migratify.entities.Track;
import com.markarko.migratify.entities.Tracks;
import com.markarko.migratify.entities.TracksWrapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrackService {

    public List<Track> getLikedSongs(String accessToken) {
        String url = "https://api.spotify.com/v1/me/tracks";

        HttpHeaders headers = buildHeaders(accessToken);

        HttpEntity request = new HttpEntity(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Tracks> response = restTemplate.exchange(url, HttpMethod.GET, request, Tracks.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody().getTracks();
        }

        return new ArrayList<>();
    }
    public List<Track> getTracks(String accessToken, String url) {
        HttpHeaders headers = buildHeaders(accessToken);

        HttpEntity request = new HttpEntity(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TracksWrapper> response = restTemplate.exchange(url, HttpMethod.GET, request, TracksWrapper.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody().getTracks();
        }

        return new ArrayList<>();
    }

    public ResponseEntity<String> postTracks(String accessToken, String url, List<String> trackUris) {
        HttpHeaders headers = buildHeaders(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> data = new HashMap<>();
        data.put("uris", trackUris);
        data.put("position", 0);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(data, headers);
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(url, HttpMethod.POST, request, String.class);
    }
    private HttpHeaders buildHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Authorization", "Bearer " + accessToken);

        return headers;
    }
}
