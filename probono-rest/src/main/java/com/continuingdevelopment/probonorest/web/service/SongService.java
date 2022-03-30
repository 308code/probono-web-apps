package com.continuingdevelopment.probonorest.web.service;

import com.continuingdevelopment.probonorest.web.model.SongDto;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface SongService {
    SongDto getSongById(UUID songId);

    UUID createSong(SongDto songDto);

    void updateSong(UUID songId, SongDto songDto);

    void deleteSong(UUID songId);
}
