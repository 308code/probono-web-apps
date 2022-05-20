package com.continuingdevelopment.probonorest.web.service;

import com.continuingdevelopment.probonorest.web.dao.SongDao;
import com.continuingdevelopment.probonorest.web.model.SongDto;
import com.continuingdevelopment.probonorest.web.model.SongDtoComparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SongServiceImpl implements SongService{
    private final SongDao songDao;
    private final SongDtoComparator songDtoComparator = new SongDtoComparator();

    public SongServiceImpl(SongDao songDao){
        this.songDao = songDao;
    }

    @Override
    public String createSong(SongDto songDto) {return songDao.save(songDto).getId();}

    @Override
    public SongDto findSongDtoById(String songId) {
        return songDao.findSongDtoById(songId);
    }

    @Override
    public List<SongDto> findAllSongs() {
        List<SongDto> songDtoList = songDao.findAll();
        songDtoList.sort(songDtoComparator);
        return songDtoList;
    }

    @Override
    public List<SongDto> getSongsPlayedBetween(Date fromDate, Date toDate) {
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
        List<SongDto> songDtoList = songDao.findSongsPlayedBetweenDates(fromDate,toDate);
        songDtoList.sort(songDtoComparator);
        return songDtoList;
    }

    @Override
    public List<SongDto> getSongTitleContains(String title){
        List<SongDto> songDtoList =  songDao.findSongDtosByTitleContaining(title);
        songDtoList.sort(songDtoComparator);
        return songDtoList;
    }

    @Override
    public List<SongDto> getSongArtistContains(String artist) {
        List<SongDto> songDtoList =  songDao.findSongDtosByArtistMatchesRegex(artist);
        songDtoList.sort(songDtoComparator);
        return songDtoList;
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
