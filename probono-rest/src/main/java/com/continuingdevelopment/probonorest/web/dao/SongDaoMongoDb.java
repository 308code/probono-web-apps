package com.continuingdevelopment.probonorest.web.dao;

import com.continuingdevelopment.probonorest.web.model.PlayedDto;
import com.continuingdevelopment.probonorest.web.model.SongDto;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@Slf4j
public class SongDaoMongoDb implements SongDao {
    private Environment env;
    public SongDaoMongoDb(Environment env){
        this.env = env;
    }
    @Override
    public SongDto getSongById(UUID id) {
        log.info("*********** SongId = " + id.toString());
        String [] aka = {"Awesome God"};
        List<PlayedDto> playedDtoList = new ArrayList<>();
        playedDtoList.add(PlayedDto.builder()
                .datePlayed(new Date(Calendar.getInstance().getTimeInMillis()))
                .notes("This is the notes section")
                .series("PC1,PC2,C1,C2,C3,|,V1,PC3,V2,|,PC1").build());
        playedDtoList.add(PlayedDto.builder()
                .datePlayed(new Date(Calendar.getInstance().getTimeInMillis()))
                .notes("This is the notes section2")
                .series("PC1,PC2,C1,C2,C3,PC1,||,PC3,|,V1,PC3,V2").build());
        return SongDto.builder()
                .id(id)
                .title("My God is a awesome God")
                .artist("Open Heaven")
                .aka(aka)
                .played(playedDtoList).build();
    }

    @Override
    public UUID createSong(SongDto songDto) {
        return UUID.randomUUID();
    }

    @Override
    public void updateSong(UUID songId, SongDto songDto) {
        log.info("*********** SongId = " + songId.toString());
        log.info(songDto.toString());
        SongDto originalSongDto = getSongById(songId);
        originalSongDto.setTitle(songDto.getTitle() + " 2");
        originalSongDto.setArtist(songDto.getArtist() + " 2");
        originalSongDto.setAka(songDto.getAka());
        originalSongDto.setPlayed(songDto.getPlayed());
    }

    @Override
    public void deleteSong(UUID songId) {
        log.info(env.getProperty("db.username"));
        log.info(System.getenv(env.getProperty("db.username")));
        log.info("Delete song: "+ songId.toString() + " was called!");
    }
    /*

    MONGO_PROBONO_PSWD=probono_1
MONGO_PROBONO_USER_NAME=probono
MONGO_PROBONO_DB_NAME=probono

    * db=MONGO_PROBONO_DB_NAME
db.username=MONGO_PROBONO_USER_NAME
db.pswd=MONGO_PROBONO_PSWD
    * */
}
