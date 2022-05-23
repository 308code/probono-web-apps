package com.continuingdevelopment.probonorest.web.service;

import com.continuingdevelopment.probonorest.web.model.WellDto;
import com.mongodb.MongoException;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface WellService {
    ResponseEntity<WellDto> getWellById(String wellId) throws MongoException;
    ResponseEntity<List<WellDto>> getAllWells() throws MongoException;
    ResponseEntity<List<WellDto>> getAllWellsByName(String title) throws MongoException;
    ResponseEntity<List<WellDto>> getAllWellsByCounty(String title) throws MongoException;
    ResponseEntity<List<WellDto>> getAllWellsProducingBetween(Date fromDate, Date toDate) throws MongoException;
    ResponseEntity<String> createWell(WellDto wellDto) throws MongoException;
    ResponseEntity<Void> updateWell(WellDto wellDto) throws MongoException;
    ResponseEntity<Void> deleteWellById(String wellId) throws MongoException;
}
