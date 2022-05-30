package com.continuingdevelopment.probonorest.web.service;

import com.continuingdevelopment.probonorest.web.dao.SongDao;
import com.continuingdevelopment.probonorest.web.model.PlayedDto;
import com.continuingdevelopment.probonorest.web.model.SongDto;
import com.flextrade.jfixture.JFixture;
import com.mongodb.MongoException;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SongServiceImplTest {
    @Mock
    SongDao mockSongDao;
    SongService songService;
    JFixture jFixture = new JFixture();
    List<SongDto> songDtoList;
    List<PlayedDto> playedDtoList;
    SongDto songDto1;
    SongDto songDto2;
    SongDto songDto3;
    PlayedDto playedDto1;
    PlayedDto playedDto2;
    PlayedDto playedDto3;

    @BeforeEach
    void setUp() {
        songService = new SongServiceImpl(mockSongDao);
        songDtoList = Lists.newArrayList(jFixture.collections().createCollection(SongDto.class));
        playedDtoList = Lists.newArrayList(jFixture.collections().createCollection(PlayedDto.class));
        songDto1 = jFixture.create(SongDto.class);
        songDto2 = jFixture.create(SongDto.class);
        songDto3 = jFixture.create(SongDto.class);
        playedDto1 = jFixture.create(PlayedDto.class);
        playedDto2 = jFixture.create(PlayedDto.class);
        playedDto3 = jFixture.create(PlayedDto.class);
    }

    @AfterEach
    void tearDown() {
        playedDto1 = null;
        playedDto2 = null;
        playedDto3 = null;
        songDto1 = null;
        songDto2 = null;
        songDto3 = null;
        playedDtoList = null;
        songDtoList = null;
    }

    @Test
    void createSong_successfully() {
        when(mockSongDao.save(songDto1)).thenReturn(songDto1);
        ResponseEntity<String> responseEntity = songService.createSong(songDto1);
        assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
    }

    @Test
    void createSong_mongoExceptionThrown() {
        when(mockSongDao.save(songDto1)).thenThrow(new MongoException("Mongo Exception is thrown!"));
        ResponseEntity<String> responseEntity = songService.createSong(songDto1);
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
    }

    @Test
    void createSong_failedWithoutException() {
        when(mockSongDao.save(songDto1)).thenReturn(null);
        ResponseEntity<String> responseEntity = songService.createSong(songDto1);
        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }

    @Test
    void getSongById_Successfully() {
        when(mockSongDao.findSongDtoById(anyString())).thenReturn(songDto2);
        ResponseEntity<SongDto> responseEntity = songService.getSongById(songDto2.getId());
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(1, Integer.valueOf(Objects.requireNonNull(responseEntity.getHeaders().get("count")).get(0)));
    }

    @Test
    void getSongById_ThrowsMongoException() {
        when(mockSongDao.findSongDtoById(songDto3.getId())).thenThrow(new MongoException("Mongo Exception Thrown!"));
        ResponseEntity<SongDto> responseEntity = songService.getSongById(songDto3.getId());
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
    }
    @Test
    void getSongById_NoExceptionThrownButReturnsNull() {
        when(mockSongDao.findSongDtoById(songDto2.getId())).thenReturn(null);
        ResponseEntity<SongDto> responseEntity = songService.getSongById(songDto2.getId());
        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
        assertEquals(0, Integer.valueOf(Objects.requireNonNull(responseEntity.getHeaders().get("count")).get(0)));
    }

    @Test
    void getAllSongs() {
        when(mockSongDao.findAllByOrderByTitleAscArtistAsc()).thenReturn(songDtoList);
        ResponseEntity<List<SongDto>> responseEntity = songService.getAllSongs();
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(3, Integer.valueOf(Objects.requireNonNull(responseEntity.getHeaders().get("count")).get(0)));
    }

    @Test
    void getAllSongs_Throws_Exception() {
        when(mockSongDao.findAllByOrderByTitleAscArtistAsc()).thenThrow(new MongoException("Threw a MongoException!"));
        ResponseEntity<List<SongDto>> responseEntity = songService.getAllSongs();
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
        assertNull(responseEntity.getHeaders().get("count"));
    }

    @Test
    void getAllSongs_Returns_Null() {
        when(mockSongDao.findAllByOrderByTitleAscArtistAsc()).thenReturn(null);
        ResponseEntity<List<SongDto>> responseEntity = songService.getAllSongs();
        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }

    @Test
    void getAllSongsPlayedBetween() {
        when(mockSongDao.findSongsPlayedBetweenDates(any(Date.class), any(Date.class))).thenReturn(songDtoList);
        ResponseEntity<List<SongDto>> responseEntity = songService.getAllSongsPlayedBetween(new Date(), new Date());
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }

    @Test
    void getAllSongsPlayedBetweenReturnsNull() {
        when(mockSongDao.findSongsPlayedBetweenDates(any(Date.class), any(Date.class))).thenReturn(null);
        ResponseEntity<List<SongDto>> responseEntity = songService.getAllSongsPlayedBetween(new Date(), new Date());
        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }

    @Test
    void getAllSongsPlayedBetween_No_Dates_Passed() {
        when(mockSongDao.findSongsPlayedBetweenDates(any(Date.class), any(Date.class))).thenReturn(songDtoList);
        ResponseEntity<List<SongDto>> responseEntity = songService.getAllSongsPlayedBetween(null, null);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }

    @Test
    void getAllSongsPlayedBetween_Dates_Passed_In_Wrong_Order() {
        Date toDate = new Date();
        Date fromDate = new Date();
        fromDate.setTime(fromDate.getTime() + 1000);
        when(mockSongDao.findSongsPlayedBetweenDates(any(Date.class), any(Date.class))).thenReturn(songDtoList);
        ResponseEntity<List<SongDto>> responseEntity = songService.getAllSongsPlayedBetween(fromDate, toDate);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }

    @Test
    void getAllSongsPlayedBetween_throws_exception() {
        when(mockSongDao.findSongsPlayedBetweenDates(any(Date.class), any(Date.class))).thenThrow(new MongoException("Threw a MongoException!"));
        ResponseEntity<List<SongDto>> responseEntity = songService.getAllSongsPlayedBetween(new Date(), new Date());
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
        assertNull(responseEntity.getHeaders().get("count"));
    }

    @Test
    void getAllSongsByTitle() {
        when(mockSongDao.findSongDtosByTitleContaining(anyString())).thenReturn(songDtoList);
        ResponseEntity<List<SongDto>> responseEntity = songService.getAllSongsByTitle("");
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }

    @Test
    void getAllSongsByTitle_Returns_Null() {
        when(mockSongDao.findSongDtosByTitleContaining(anyString())).thenReturn(null);
        ResponseEntity<List<SongDto>> responseEntity = songService.getAllSongsByTitle("");
        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }

    @Test
    void getAllSongsByTitle_throws_exception() {
        when(mockSongDao.findSongDtosByTitleContaining(anyString())).thenThrow(new MongoException("Mongo Exception was thrown!"));
        ResponseEntity<List<SongDto>> responseEntity = songService.getAllSongsByTitle("");
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
        assertNull(responseEntity.getHeaders().get("count"));
    }

    @Test
    void getAllSongsByArtist() {
        when(mockSongDao.findSongDtosByArtistMatchesRegex(anyString())).thenReturn(songDtoList);
        ResponseEntity<List<SongDto>> responseEntity = songService.getAllSongsByArtist("");
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }

    @Test
    void getAllSongsByArtist_Returns_Null() {
        when(mockSongDao.findSongDtosByArtistMatchesRegex(anyString())).thenReturn(null);
        ResponseEntity<List<SongDto>> responseEntity = songService.getAllSongsByArtist("");
        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }

    @Test
    void getAllSongsByArtist_throws_exception() {
        when(mockSongDao.findSongDtosByArtistMatchesRegex(anyString())).thenThrow(new MongoException("Mongo Exception was thrown!"));
        ResponseEntity<List<SongDto>> responseEntity = songService.getAllSongsByArtist("");
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
        assertNull(responseEntity.getHeaders().get("count"));
    }

    @Test
    void updateSong() {
        when(mockSongDao.save(songDto1)).thenReturn(songDto1);
        ResponseEntity<Void> responseEntity = songService.updateSong(songDto1);
        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }

    @Test
    void updateSong_throws_exception() {
        when(mockSongDao.save(songDto1)).thenThrow(new MongoException("Mongo Exception was thrown!"));
        ResponseEntity<Void> responseEntity = songService.updateSong(songDto1);
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
    }

    @Test
    void deleteSong() {
        doNothing().when(mockSongDao).deleteById(anyString());
        ResponseEntity<Void> responseEntity = songService.deleteSong("someSongId");
        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }

    @Test
    void deleteSong_throws_exception() {
        doThrow(new MongoException("Mongo Exception was thrown!")).when(mockSongDao).deleteById(anyString());
        ResponseEntity<Void> responseEntity = songService.deleteSong("someSongId");
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
    }
}