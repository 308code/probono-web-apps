package com.continuingdevelopment.probonorest.web.service;

import com.continuingdevelopment.probonorest.web.model.WellDto;
import com.mongodb.MongoException;

import java.util.Date;
import java.util.List;

public interface WellService {
    WellDto findWellById(String wellId) throws MongoException;
    List<WellDto> findAllWells() throws MongoException;
    List<WellDto> getWellsWhereWellNameContains(String title) throws MongoException;
    List<WellDto> getWellsWhereCountyContains(String title) throws MongoException;
    List<WellDto> getWellsProducingBetween(Date fromDate, Date toDate) throws MongoException;
    String createWell(WellDto wellDto) throws MongoException;
    void updateWell(WellDto wellDto) throws MongoException;
    void deleteWellById(String wellId) throws MongoException;
}
