package com.continuingdevelopment.probonorest.web.dao;

import com.continuingdevelopment.probonorest.web.model.SongDto;
import com.continuingdevelopment.probonorest.web.model.WellDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface WellDao extends MongoRepository<WellDto, String> {

    //Insert WellDto
    WellDto insert(WellDto wellDto);

    //Find all wells in the camphire_drilling_wells collection
    List<WellDto> findAll();

    //Find well with a particular id
    WellDto findWellDtoById(String id);

    //Find all wells that containe the incoming string
    List<WellDto> findWellDtosByWellNameContains(String wellName);

    //Find all wells from a particular county
    List<WellDto> findWellDtosByCountyContains(String county);

    //Find wells production in the given date range ad returned Sorted by county then wellName then wellNumber in ASC order
    //TODO fix this so the date range search works as expected
    @Query(  value="{ '$and' : [ { 'production.payedDate' : { '$gte' : { $date : ?0}}} , { 'production.payedDate' : { '$lte' : { $date : ?1 }} } ]}")
    List<WellDto> findWellDtosByProductionDates(Date fromDate, Date toDate);

    //Update well
    WellDto save(WellDto wellDto);

    //Delete well with particular id
    void deleteWellDtoById(String wellId);

}
