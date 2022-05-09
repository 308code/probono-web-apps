package com.continuingdevelopment.probonorest.web.dao;

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
    List<WellDto> findAllByOrderByCountyAscTownshipAscWellNameAscWellNumberAsc();

    //Find well with a particular id
    WellDto findWellDtoById(String id);

    //Find all wells that containe the incoming string
    @Query(value= "{'wellName': { '$regularExpression' : { 'pattern' : ?0, 'options' : 'i'}}}", sort ="{county:1 , township:1, wellName:1 , wellNumber:1}" )
    List<WellDto> findWellDtosByWellNameRegexOrderByCountyAscTownshipAscWellNameAscWellNumberAsc(String wellName);

    //Find all wells from a particular county
    @Query(value= "{'county': { '$regularExpression' : { 'pattern' : ?0, 'options' : 'i'}}}", sort ="{county:1 , township:1, wellName:1 , wellNumber:1}" )
    List<WellDto> findWellDtosByCountyRegexIgnoreCaseOrderByTownshipAscWellNameAscWellNumberAsc(String county);

    //Find wells production in the given date range ad returned Sorted by county then township then wellName then wellNumber all in ASC order
    @Query(value = "{ 'production.payedDate' : { '$gt' : { '$date' : ?0}, '$lt' : { '$date' : ?1}}}", sort= "{ county:1 , township:1, wellName:1 , wellNumber:1}")
    List<WellDto> findWellDtosByProductionPayedDateBetweenOrderByCountyAscTownshipAscWellNameAscWellNumberAsc(Date fromDate, Date toDate);

    //Update well
    WellDto save(WellDto wellDto);

    //Delete well with particular id
    void deleteWellDtoById(String wellId);

}
