package com.continuingdevelopment.probonorest.web.controller;

import com.continuingdevelopment.probonorest.web.aspects.LogMethod;
import com.continuingdevelopment.probonorest.web.model.SongDto;
import com.continuingdevelopment.probonorest.web.service.SongService;
import com.mongodb.MongoException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/songs")
public class SongController {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    private final SongService songService;

    public SongController(SongService songService){
        this.songService = songService;
    }

    @LogMethod(level = "INFO")
    @PostMapping
    public ResponseEntity<String> insertSong(@RequestBody SongDto songDto) {
        String newSongId;
        try {
            newSongId = songService.createSong(songDto);
        } catch (MongoException e) {
            log.error("Error creating song: {}  MESSAGE: {}  STACKTRACE: {}", songDto, e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        if (StringUtils.isEmpty(newSongId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        headers.add("location", "http://localhost:8080/api/songs/" + newSongId);
        return new ResponseEntity<>(newSongId, headers, HttpStatus.CREATED);
    }

    @LogMethod(level = "INFO")
    @GetMapping("/{songId}")
    public ResponseEntity<SongDto> getSong(@PathVariable("songId") String songId) {
        SongDto songDto;
        try {
            songDto = songService.findSongDtoById(songId);
        } catch (MongoException e) {
            log.error("Error getting song that has id: {}  MESSAGE: {}  STACKTRACE: {}", songId, e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (ObjectUtils.isEmpty(songDto)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(songDto, HttpStatus.OK);
    }

    @LogMethod(level = "INFO")
    @GetMapping()
    public ResponseEntity<List<SongDto>> getAllSongs() {
        List<SongDto> songDtoList;
        try {
            songDtoList = songService.findAllSongs();
        } catch (MongoException e) {
            log.error("Error getting all songs: MESSAGE: {}  STACKTRACE: {}", e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = addCountToHeader(songDtoList);
        return getListResponseEntity(songDtoList, headers);
    }

    @LogMethod(level = "INFO")
    @GetMapping("/{fromDate}/{toDate}")
    public ResponseEntity<List<SongDto>> getSongsPlayedBetween(
            @PathVariable("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @PathVariable("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
        List<SongDto> songDtoList;
        try {
            songDtoList = songService.getSongsPlayedBetween(fromDate, toDate);
        } catch (MongoException e) {
            log.error("Error getting played within the date range of FROM: {}  TO: {}  MESSAGE: {}  STACKTRACE: {}",
                    SDF.format(toDate), SDF.format(toDate), e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = addCountToHeader(songDtoList);
        return getListResponseEntity(songDtoList, headers);
    }

    @LogMethod(level = "INFO")
    @GetMapping("/title/contains/{title}")
    public ResponseEntity<List<SongDto>> getSongTitleContains(@PathVariable("title") String title) {
        List<SongDto> songDtoList;
        try {
            songDtoList = songService.getSongTitleContains(title);
        } catch (MongoException e) {
            log.error("Error getting songs that title contains string: {}  MESSAGE: {}  STACKTRACE: {}", title, e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = addCountToHeader(songDtoList);
        return getListResponseEntity(songDtoList, headers);
    }

    @LogMethod(level = "INFO")
    @GetMapping("/artist/contains/{artist}")
    public ResponseEntity<List<SongDto>> getSongArtistContains(@PathVariable("artist") String artist) {
        List<SongDto> songDtoList;
        try {
            songDtoList = songService.getSongArtistContains(artist);
        } catch (MongoException e) {
            log.error("Error gettings songs that artist contains string: {}  MESSAGE: {}  STACKTRACE: {}", artist, e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = addCountToHeader(songDtoList);
        return getListResponseEntity(songDtoList, headers);
    }

    @LogMethod(level = "INFO")
    @PutMapping()
    public ResponseEntity<Void> updateSong(@RequestBody SongDto songDto){
        try {
            songService.updateSong(songDto);
        }catch (MongoException e){
            log.error("Error Updating song: {}  MESSAGE: {}  STACKTRACE: {}" , songDto, e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @LogMethod(level = "INFO")
    @DeleteMapping("/{songId}")
    public ResponseEntity<Void> deleteSong(@PathVariable("songId") String songId){
        try {
            songService.deleteSong(songId);
        }catch (MongoException e){
            log.error("Error Deleting song ID: {}  MESSAGE: {}  STACKTRACE: {}" , songId, e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private HttpHeaders addCountToHeader(List<SongDto> songDtoList){
        if(CollectionUtils.isEmpty(songDtoList)){
            songDtoList = new ArrayList<>();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("count", String.valueOf(songDtoList.size()));
        return headers;
    }

    private HttpHeaders addCountToHeader(SongDto songDto){
        HttpHeaders headers = new HttpHeaders();

        if(ObjectUtils.isEmpty(songDto)){
            headers.add("count", String.valueOf(0));
        }else {
            headers.add("count", String.valueOf(1));
        }
        return headers;
    }

    private ResponseEntity<List<SongDto>> getListResponseEntity(List<SongDto> songDtoList, HttpHeaders headers) {
        if(CollectionUtils.isEmpty(songDtoList)){
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(songDtoList, headers, HttpStatus.OK);
    }
}
