package com.continuingdevelopment.probonorest.web.controller;

import com.continuingdevelopment.probonorest.web.model.SongDto;
import com.continuingdevelopment.probonorest.web.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService){
        this.songService = songService;
    }

    @PostMapping
    public ResponseEntity<String> insertSong(@RequestBody SongDto songDto){
        String newSongId = songService.createSong(songDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("location","http://localhost:8080/api/songs/" + newSongId);
        return new ResponseEntity<>(headers,HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<SongDto>> getAllSongs(){
        return new ResponseEntity<>(songService.findAllSongs(),HttpStatus.OK);
    }

    @GetMapping("/{fromDate}/{toDate}")
    public ResponseEntity<List<SongDto>> getSongsPlayedBetween(@PathVariable("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
                                                               @PathVariable("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate){
        return new ResponseEntity<>(this.songService.getSongsPlayedBetween(fromDate,toDate), HttpStatus.OK);
    }

    @GetMapping("/{songId}")
    public ResponseEntity<SongDto> getSong(@PathVariable("songId") String songId){
        return new ResponseEntity<>(this.songService.findSongDtoById(songId), HttpStatus.OK);
    }


    @GetMapping("/title/contains/{title}")
    public ResponseEntity<List<SongDto>> getSongTitleContains(@PathVariable("title") String title){
        return new ResponseEntity<>(this.songService.getSongTitleContains(title), HttpStatus.OK);
    }

    @GetMapping("/artist/contains/{artist}")
    public ResponseEntity<List<SongDto>> getSongArtistContains(@PathVariable("artist") String artist){
        return new ResponseEntity<>(this.songService.getSongArtistContains(artist), HttpStatus.OK);
    }



    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSong(@RequestBody SongDto songDto){
        this.songService.updateSong(songDto);
    }

    @DeleteMapping("/{songId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSong(@PathVariable("songId") String songId){
        this.songService.deleteSong(songId);
    }
}
