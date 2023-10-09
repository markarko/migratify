package com.markarko.migratify.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import java.util.List;

@Getter
public class Tracks {
    @JsonProperty("items")
    private List<Track> tracks;
}

