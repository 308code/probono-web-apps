package com.continuingdevelopment.probonorest.web.controller;

import com.continuingdevelopment.probonorest.web.aspects.LogMethod;
import com.continuingdevelopment.probonorest.web.model.SongDto;
import com.continuingdevelopment.probonorest.web.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/songs")
public class SongController {


    private final SongService songService;

    public SongController(SongService songService){
        this.songService = songService;
    }

    @LogMethod(level = "INFO")
    @PostMapping
    public ResponseEntity<String> insertSong(@RequestBody SongDto songDto) {
        return songService.createSong(songDto);
    }

    @LogMethod(level = "INFO")
    @GetMapping("/{songId}")
    public ResponseEntity<SongDto> getSong(@PathVariable("songId") String songId) {
        return songService.getSongById(songId);
    }

    @LogMethod(level = "INFO")
    @GetMapping()
    public ResponseEntity<List<SongDto>> getAllSongs() {
        return songService.getAllSongs();
    }

    @LogMethod(level = "INFO")
    @GetMapping("/{fromDate}/{toDate}")
    public ResponseEntity<List<SongDto>> getSongsPlayedBetween(
            @PathVariable("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @PathVariable("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
        return songService.getAllSongsPlayedBetween(fromDate, toDate);
    }

    @LogMethod(level = "INFO")
    @GetMapping("/title/contains/{title}")
    public ResponseEntity<List<SongDto>> getSongTitleContains(@PathVariable("title") String title) {
        return songService.getAllSongsByTitle(title);
    }

    @LogMethod(level = "INFO")
    @GetMapping("/artist/contains/{artist}")
    public ResponseEntity<List<SongDto>> getSongArtistContains(@PathVariable("artist") String artist) {
        return songService.getAllSongsByArtist(artist);
    }

    @LogMethod(level = "INFO")
    @PutMapping()
    public ResponseEntity<Void> updateSong(@RequestBody SongDto songDto){
        return songService.updateSong(songDto);
    }

    @LogMethod(level = "INFO")
    @DeleteMapping("/{songId}")
    public ResponseEntity<Void> deleteSong(@PathVariable("songId") String songId){
        return songService.deleteSong(songId);
    }


}
