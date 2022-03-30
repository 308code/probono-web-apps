package com.continuingdevelopment.probonorest.web.dao;

import com.continuingdevelopment.probonorest.web.model.SongDto;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface SongDao {
    SongDto getSongById(UUID id);

    UUID createSong(SongDto songDto);

    void updateSong(UUID songId, SongDto songDto);

    void deleteSong(UUID songId);
}
