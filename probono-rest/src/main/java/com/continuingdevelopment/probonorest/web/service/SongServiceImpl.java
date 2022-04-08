package com.continuingdevelopment.probonorest.web.service;

import com.continuingdevelopment.probonorest.web.dao.SongDao;
import com.continuingdevelopment.probonorest.web.model.SongDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class SongServiceImpl implements SongService{
    private SongDao songDao;

    public SongServiceImpl(SongDao songDao){
        this.songDao = songDao;
    }

    @Override
    public SongDto findSongDtoById(String songId) {
        return songDao.findSongDtoById(songId);
    }

    @Override
    public List<SongDto> findAllSongs() {
        return songDao.findAll();
    }

    @Override
    public List<SongDto> getSongsPlayedBetween(Date fromDate, Date toDate) {
        return songDao.findSongsPlayedBetweenDates(fromDate,toDate);
    }

    @Override
    public List<SongDto> getSongTitleContains(String title){
        return songDao.findSongDtosByTitleMatchesRegexOrAkaMatchesRegex(title,title);
    }

    @Override
    public List<SongDto> getSongArtistContains(String artist) {
        return songDao.findSongDtosByArtistMatchesRegex(artist);
    }

    @Override
    public String createSong(SongDto songDto) {
        return songDao.save(songDto).getId();
    }

    @Override
    public void updateSong(SongDto songDto) {
        songDao.save(songDto);
    }

    @Override
    public void deleteSong(String songId) {
        songDao.deleteById(songId);
    }
}
