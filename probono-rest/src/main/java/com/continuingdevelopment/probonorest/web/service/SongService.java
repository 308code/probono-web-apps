package com.continuingdevelopment.probonorest.web.service;

import com.continuingdevelopment.probonorest.web.model.SongDto;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface SongService {
    SongDto findSongDtoById(String songId);
    List<SongDto> findAllSongs();
    List<SongDto> getSongTitleContains(String title);
    List<SongDto> getSongArtistContains(String title);
    List<SongDto> getSongsPlayedBetween(Date fromDate, Date toDate);
    String createSong(SongDto songDto);

    void updateSong(SongDto songDto);

    void deleteSong(String songId);
}
