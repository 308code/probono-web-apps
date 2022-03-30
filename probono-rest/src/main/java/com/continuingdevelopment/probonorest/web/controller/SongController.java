package com.continuingdevelopment.probonorest.web.controller;

import com.continuingdevelopment.probonorest.web.model.SongDto;
import com.continuingdevelopment.probonorest.web.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/song")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService){
        this.songService = songService;
    }

    @GetMapping("/{songId}")
    public ResponseEntity<SongDto> getSong(@PathVariable("songId") UUID songId){
        return new ResponseEntity<>(this.songService.getSongById(songId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UUID> createSong(@RequestBody SongDto songDto){
        UUID newSongId = songService.createSong(songDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("location","http://localhost:8080/api/v1/song/" + newSongId.toString());
        return new ResponseEntity<>(headers,HttpStatus.CREATED);
    }

    @PutMapping("/{songId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSong(@PathVariable("songId") UUID songId, @RequestBody SongDto songDto){
        this.songService.updateSong(songId,songDto);
    }

    @DeleteMapping("/{songId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSong(@PathVariable("songId") UUID songId){
        this.songService.deleteSong(songId);
    }
}
