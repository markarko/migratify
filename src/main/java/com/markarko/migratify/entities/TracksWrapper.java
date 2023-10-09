package com.markarko.migratify.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

public class TracksWrapper {
    @JsonProperty("tracks")
    private Tracks tracksWrapper;

    public List<Track> getTracks() {
        return tracksWrapper.getTracks();
    }
}