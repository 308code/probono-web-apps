package com.continuingdevelopment.probonorest.web.service;

import com.continuingdevelopment.probonorest.web.dao.SongDao;
import com.continuingdevelopment.probonorest.web.model.SongDto;
import com.continuingdevelopment.probonorest.web.model.SongDtoComparator;
import com.mongodb.MongoException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SongServiceImpl implements SongService{
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    private final SongDao songDao;
    private final SongDtoComparator songDtoComparator = new SongDtoComparator();

    public SongServiceImpl(SongDao songDao){
        this.songDao = songDao;
    }

    @Override
    public ResponseEntity<String> createSong(SongDto songDto) {
        SongDto responseSongDto;
        String newSongId;
        try{
            responseSongDto = songDao.save(songDto);
            newSongId = ObjectUtils.isEmpty(responseSongDto) ? "" : responseSongDto.getId();
        }catch (MongoException e){
            log.error("Error creating song: {}  MESSAGE: {}  STACKTRACE: {}", songDto, e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        if (StringUtils.isBlank(newSongId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        headers.add("location", "http://localhost:8080/api/songs/" + newSongId);
        return new ResponseEntity<>(newSongId, headers, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<SongDto> getSongById(String songId) {
        SongDto songDto;

        try {
            songDto = songDao.findSongDtoById(songId);
        } catch (MongoException e) {
            log.error("Error getting song that has id: {}  MESSAGE: {}  STACKTRACE: {}", songId, e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = addCountToHeader(songDto);
        if (ObjectUtils.isEmpty(songDto)) {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(songDto, headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<SongDto>> getAllSongs() {
        List<SongDto> songDtoList;
        try {
            songDtoList = songDao.findAllByOrderByTitleAscArtistAsc();
        } catch (MongoException e) {
            log.error("Error getting all songs: MESSAGE: {}  STACKTRACE: {}", e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(ObjectUtils.isNotEmpty(songDtoList)){
            songDtoList.sort(songDtoComparator);
        }
        HttpHeaders headers = addCountToHeader(songDtoList);
        return getListResponseEntity(songDtoList, headers);
    }

    @Override
    public ResponseEntity<List<SongDto>> getAllSongsPlayedBetween(Date fromDate, Date toDate) {
        if(ObjectUtils.isEmpty(fromDate)){
            Calendar today = Calendar.getInstance();
            today.set(Calendar.YEAR, today.get(Calendar.YEAR) - 1);
            fromDate = today.getTime();
        }
        if(ObjectUtils.isEmpty(toDate)){
            Calendar today = Calendar.getInstance();
            toDate = today.getTime();
        }
        if(fromDate.after(toDate)){
            Date temp = fromDate;
            fromDate = toDate;
            toDate = temp;
        }

        List<SongDto> songDtoList;
        try {
            songDtoList = songDao.findSongsPlayedBetweenDates(fromDate, toDate);
        }catch (MongoException e) {
            log.error("Error getting played within the date range of FROM: {}  TO: {}  MESSAGE: {}  STACKTRACE: {}",
                    SDF.format(toDate), SDF.format(toDate), e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(ObjectUtils.isNotEmpty(songDtoList)){
            songDtoList.sort(songDtoComparator);
        }
        HttpHeaders headers = addCountToHeader(songDtoList);
        return getListResponseEntity(songDtoList, headers);
    }

    @Override
    public ResponseEntity<List<SongDto>> getAllSongsByTitle(String title){
        List<SongDto> songDtoList;
        try {
            songDtoList =  songDao.findSongDtosByTitleContaining(title);
        } catch (MongoException e) {
            log.error("Error getting songs that title contains string: {}  MESSAGE: {}  STACKTRACE: {}", title, e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(ObjectUtils.isNotEmpty(songDtoList)){
            songDtoList.sort(songDtoComparator);
        }
        HttpHeaders headers = addCountToHeader(songDtoList);
        return getListResponseEntity(songDtoList, headers);
    }

    @Override
    public ResponseEntity<List<SongDto>> getAllSongsByArtist(String artist) {
        List<SongDto> songDtoList;
        try {
            songDtoList = songDao.findSongDtosByArtistMatchesRegex(artist);
        } catch (MongoException e) {
            log.error("Error gettings songs that artist contains string: {}  MESSAGE: {}  STACKTRACE: {}", artist, e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(ObjectUtils.isNotEmpty(songDtoList)){
            songDtoList.sort(songDtoComparator);
        }
        HttpHeaders headers = addCountToHeader(songDtoList);
        return getListResponseEntity(songDtoList, headers);
    }

    @Override
    public ResponseEntity<Void> updateSong(SongDto songDto) {
        try {
            songDao.save(songDto);
        }catch (MongoException e){
            log.error("Error Updating song: {}  MESSAGE: {}  STACKTRACE: {}" , songDto, e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> deleteSong(String songId) {
        try {
            songDao.deleteById(songId);
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
