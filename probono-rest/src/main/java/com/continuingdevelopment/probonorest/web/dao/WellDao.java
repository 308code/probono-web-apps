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

    //Find all wells in the camphire_drilling_wells collection
    List<WellDto> findAll();

    //Find well with a particular id
    WellDto findWellDtoById(String id);

}
