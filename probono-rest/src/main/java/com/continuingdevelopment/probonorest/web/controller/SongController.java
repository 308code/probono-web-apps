package com.continuingdevelopment.probonorest.web.controller;

import com.continuingdevelopment.probonorest.web.model.SongDto;
import com.continuingdevelopment.probonorest.web.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    @PostMapping
    public ResponseEntity<String> insertSong(@RequestBody SongDto songDto){
        String newSongId = songService.createSong(songDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("location","http://localhost:8080/api/songs/" + newSongId);
        return new ResponseEntity<>(headers,HttpStatus.CREATED);
    }

    @GetMapping("/{songId}")
    public ResponseEntity<SongDto> getSong(@PathVariable("songId") String songId){
        return new ResponseEntity<>(songService.findSongDtoById(songId), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<SongDto>> getAllSongs(){
        List<SongDto> songDtoList = songService.findAllSongs();
        HttpHeaders headers = addCountToHeader(songDtoList);
        return new ResponseEntity<>(songDtoList,headers,HttpStatus.OK);
    }

    @GetMapping("/{fromDate}/{toDate}")
    public ResponseEntity<List<SongDto>> getSongsPlayedBetween(@PathVariable("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
                                                               @PathVariable("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate){
        List<SongDto> songDtoList = songService.getSongsPlayedBetween(fromDate,toDate);
        HttpHeaders headers = addCountToHeader(songDtoList);
        return new ResponseEntity<>(songDtoList, headers, HttpStatus.OK);
    }

    @GetMapping("/title/contains/{title}")
    public ResponseEntity<List<SongDto>> getSongTitleContains(@PathVariable("title") String title){
        List<SongDto> songDtoList = songService.getSongTitleContains(title);
        HttpHeaders headers = addCountToHeader(songDtoList);
        return new ResponseEntity<>(songDtoList, headers, HttpStatus.OK);
    }

    @GetMapping("/artist/contains/{artist}")
    public ResponseEntity<List<SongDto>> getSongArtistContains(@PathVariable("artist") String artist){
        List<SongDto> songDtoList = songService.getSongArtistContains(artist);
        HttpHeaders headers = addCountToHeader(songDtoList);
        return new ResponseEntity<>(songDtoList, headers, HttpStatus.OK);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSong(@RequestBody SongDto songDto){
        songService.updateSong(songDto);
    }

    @DeleteMapping("/{songId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSong(@PathVariable("songId") String songId){
        songService.deleteSong(songId);
    }

    private HttpHeaders addCountToHeader(List<SongDto> songDtoList){
        if(CollectionUtils.isEmpty(songDtoList)){
            songDtoList = new ArrayList<>();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("count", String.valueOf(songDtoList.size()));
        return headers;
    }
}
