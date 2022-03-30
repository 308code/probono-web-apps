package com.continuingdevelopment.probonorest.web.service;

import com.continuingdevelopment.probonorest.web.dao.SongDao;
import com.continuingdevelopment.probonorest.web.model.SongDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SongServiceImpl implements SongService{
    private SongDao songDao;

    public SongServiceImpl(SongDao songDao){
        this.songDao = songDao;
    }

    @Override
    public SongDto getSongById(UUID songId) {
        return songDao.getSongById(songId);
    }

    @Override
    public UUID createSong(SongDto songDto) {
        return songDao.createSong(songDto);
    }

    @Override
    public void updateSong(UUID songId, SongDto songDto) {
        songDao.updateSong(songId,songDto);
    }

    @Override
    public void deleteSong(UUID songId) {
        songDao.deleteSong(songId);
    }
}
