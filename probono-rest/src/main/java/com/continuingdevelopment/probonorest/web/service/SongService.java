package com.continuingdevelopment.probonorest.web.service;

import com.continuingdevelopment.probonorest.web.model.SongDto;
import com.mongodb.MongoException;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface SongService {
    SongDto findSongDtoById(String songId) throws MongoException;
    List<SongDto> findAllSongs() throws MongoException;
    List<SongDto> getSongTitleContains(String title) throws MongoException;
    List<SongDto> getSongArtistContains(String title) throws MongoException;
    List<SongDto> getSongsPlayedBetween(Date fromDate, Date toDate) throws MongoException;
    String createSong(SongDto songDto) throws MongoException;

    void updateSong(SongDto songDto) throws MongoException;

    void deleteSong(String songId) throws MongoException;
}
