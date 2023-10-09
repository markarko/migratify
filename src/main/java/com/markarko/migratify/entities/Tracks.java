package com.markarko.migratify.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;


public class Tracks {
    @JsonProperty("tracks")
    private TracksWrapper tracksWrapper;

    public List<Track> getTracks() {
        return tracksWrapper.getTracks();
    }
    @Getter
    static class TracksWrapper {
        @JsonProperty("items")
        private List<Track> tracks;
    }
}
