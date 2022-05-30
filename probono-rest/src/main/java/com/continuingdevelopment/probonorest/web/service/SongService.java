package com.continuingdevelopment.probonorest.web.service;

import com.continuingdevelopment.probonorest.web.model.SongDto;
import com.mongodb.MongoException;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
public interface SongService {
    ResponseEntity<SongDto> getSongById(String songId) throws MongoException;
    ResponseEntity<List<SongDto>> getAllSongs() throws MongoException;
    ResponseEntity<List<SongDto>> getAllSongsByTitle(String title) throws MongoException;
    ResponseEntity<List<SongDto>> getAllSongsByArtist(String artist) throws MongoException;
    ResponseEntity<List<SongDto>> getAllSongsPlayedBetween(Date fromDate, Date toDate) throws MongoException;
    ResponseEntity<String> createSong(SongDto songDto) throws MongoException;

    ResponseEntity<Void> updateSong(SongDto songDto) throws MongoException;

    ResponseEntity<Void> deleteSong(String songId) throws MongoException;
}
