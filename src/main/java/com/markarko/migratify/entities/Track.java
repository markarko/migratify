package com.markarko.migratify.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class Track {
    @JsonProperty("track")
    private TrackWrapper trackWrapper;

    public String getUri() {
        return trackWrapper.getUri();
    }
    @Getter
    static class TrackWrapper {
        @JsonProperty("uri")
        private String uri;
    }
}
