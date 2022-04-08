package com.continuingdevelopment.probonorest.web.dao;

import com.continuingdevelopment.probonorest.web.model.SongDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface SongDao extends MongoRepository<SongDto, String> {

    //Find the song stored at id value
    SongDto findSongDtoById(String id);

    // Create a new song
    SongDto insert(SongDto songDto);

    //Find all songs in the flcSongs collection
    List<SongDto> findAll();

    //Find songs played in the given date range ad returned Sorted by title in ASC order
    @Query(value="{ '$and' : [ { 'played.datePlayed' : {'$gte' : { $date : ?0 }}} , { 'played.datePlayed' : {'$lte' : { $date : ?1 }}}]}")
    List<SongDto> findSongsPlayedBetweenDates(Date fromDate, Date toDate);

    //Find songs that contain the text in their title or in the aka array
    @Query(value = "{ '$or' : [{ 'title' : { '$regularExpression' : " +
            "{ 'pattern' : '?0', 'options' : 'i'}}}, { 'aka' : { '$regularExpression' : " +
            "{ 'pattern' : '?0', 'options' : 'i'}}}]}")
    List<SongDto> findSongDtosByTitleMatchesRegexOrAkaMatchesRegex(String title1, String title2);

    //Find songs that contain the text in the artist field
    @Query(value = "{'artist' : { '$regex' : ?0, $options: 'i'}}")
    List<SongDto> findSongDtosByArtistMatchesRegex(String artist);

    // Update or create, if song doesn't exist
    SongDto save(SongDto songDto);

    void deleteById(String songId);
}
