package com.markarko.migratify.services;

import com.markarko.migratify.entities.Track;
import com.markarko.migratify.entities.Tracks;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrackService {

    public List<Track> getTracks(String accessToken, String url) {
        HttpHeaders headers = buildHeaders(accessToken);

        HttpEntity request = new HttpEntity(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Tracks> response = restTemplate.exchange(url, HttpMethod.GET, request, Tracks.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody().getTracks();
        }

        return new ArrayList<>();
    }

    private HttpHeaders buildHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Authorization", "Bearer " + accessToken);

        return headers;
    }
}
