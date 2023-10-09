package com.markarko.migratify.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class Playlists {
    @JsonProperty("items")
    private List<Playlist> playlists;
}
