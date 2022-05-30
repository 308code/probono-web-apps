package com.continuingdevelopment.probonorest.web.service;

import com.continuingdevelopment.probonorest.web.dao.WellDao;
import com.continuingdevelopment.probonorest.web.model.WellDto;
import com.continuingdevelopment.probonorest.web.model.WellDtoComparator;
import com.mongodb.MongoException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class WellServiceImpl implements WellService{
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    private final WellDtoComparator wellDtoComparator = new WellDtoComparator();
    private final WellDao wellDao;

    public WellServiceImpl(WellDao wellDao){
        this.wellDao = wellDao;
    }

    @Override
    public ResponseEntity<String> createWell(WellDto wellDto) {
        WellDto actual;
        try{
            actual = wellDao.insert(wellDto);
        }catch (MongoException e){
            log.error("Error creating well: {} MESSAGE: {}  STACKTRACE: {}", wellDto, e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(ObjectUtils.isEmpty(actual)){
            return new ResponseEntity<>("Create Failed!",HttpStatus.NOT_FOUND);
        }
        String newWellId = actual.getId();
        if(StringUtils.isBlank(newWellId)) {
            return new ResponseEntity<>("Create Failed!",HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("location", "http://localhost:8080/api/wells/" + newWellId);

        return new ResponseEntity<>(newWellId, headers, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<WellDto> getWellById(String wellId) {
        WellDto wellDto;
        try {
            wellDto = wellDao.findWellDtoById(wellId);
        }catch (MongoException e){
            log.error("Error getting well with id: {} MESSAGE: {}  STACKTRACE: {}", wellId, e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = addCountToHeader(wellDto);
        if(ObjectUtils.isEmpty(wellDto)){
            return new ResponseEntity<>(headers,HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(wellDto,headers,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<WellDto>> getAllWells() {
        List<WellDto> wellDtoList;
        try {
            wellDtoList = wellDao.findAllByOrderByCountyAscTownshipAscWellNameAscWellNumberAsc();
        }catch (MongoException e){
            log.error("Error getting all wells: MESSAGE: {}  STACKTRACE: {}", e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(CollectionUtils.isNotEmpty(wellDtoList)){
            wellDtoList.sort(wellDtoComparator);
        }

        HttpHeaders headers = addCountToHeader(wellDtoList);
        return getListResponseEntity(wellDtoList, headers);
    }

    @Override
    public ResponseEntity<List<WellDto>> getAllWellsByName(String wellName) {
        List<WellDto> wellDtoList;
        try {
            wellDtoList = wellDao.findWellDtosByWellNameRegexOrderByCountyAscTownshipAscWellNameAscWellNumberAsc(wellName);
        }catch (MongoException e){
            log.error("Error getting all wells who's name contains: {} MESSAGE: {}  STACKTRACE: {}",wellName, e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(CollectionUtils.isNotEmpty(wellDtoList)){
            wellDtoList.sort(wellDtoComparator);
        }

        HttpHeaders headers = addCountToHeader(wellDtoList);
        return getListResponseEntity(wellDtoList, headers);
    }

    @Override
    public ResponseEntity<List<WellDto>> getAllWellsByCounty(String county) {
        List<WellDto> wellDtoList;
        try {
            wellDtoList = wellDao.findWellDtosByCountyRegexIgnoreCaseOrderByTownshipAscWellNameAscWellNumberAsc(county);

        }catch (MongoException e){
            log.error("Error getting all wells who's county name contains: {} MESSAGE: {}  STACKTRACE: {}",county, e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(CollectionUtils.isNotEmpty(wellDtoList)){
            wellDtoList.sort(wellDtoComparator);
        }
        HttpHeaders headers = addCountToHeader(wellDtoList);
        return getListResponseEntity(wellDtoList, headers);
    }

    @Override
    public ResponseEntity<List<WellDto>> getAllWellsProducingBetween(Date fromDate, Date toDate) {
        List<WellDto> wellDtoList;
        try {
            wellDtoList = wellDao.findWellDtosByProductionPayedDateBetweenOrderByCountyAscTownshipAscWellNameAscWellNumberAsc(fromDate,toDate);
        }catch (MongoException e){
            log.error("Error getting all wells that contain production between: {} and {} MESSAGE: {}  STACKTRACE: {}",
                    SDF.format(fromDate), SDF.format(toDate), e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(CollectionUtils.isNotEmpty(wellDtoList)){
            wellDtoList.sort(wellDtoComparator);
        }
        HttpHeaders headers = addCountToHeader(wellDtoList);
        return getListResponseEntity(wellDtoList, headers);
    }

    @Override
    public ResponseEntity<Void> updateWell(WellDto wellDto) {
        try {
            wellDao.save(wellDto);
        }catch (MongoException e){
            log.error("Error Updating well: {}  MESSAGE: {}  STACKTRACE: {}" , wellDto, e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> deleteWellById(String wellId) {
        try {
            wellDao.deleteWellDtoById(wellId);
        }catch (MongoException e){
            log.error("Error Deleting well with id: {}  MESSAGE: {}  STACKTRACE: {}" , wellId, e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private HttpHeaders addCountToHeader(List<WellDto> wellDtoList){
        HttpHeaders headers = new HttpHeaders();

        if(CollectionUtils.isEmpty(wellDtoList)){
            wellDtoList = new ArrayList<>(0);
        }
        headers.add("count", String.valueOf(wellDtoList.size()));
        return headers;
    }

    private HttpHeaders addCountToHeader(WellDto wellDto){
        HttpHeaders headers = new HttpHeaders();

        if(ObjectUtils.isEmpty(wellDto)){
            headers.add("count", String.valueOf(0));
        }else {
            headers.add("count", String.valueOf(1));
        }
        return headers;
    }

    private ResponseEntity<List<WellDto>> getListResponseEntity(List<WellDto> wellDtoList, HttpHeaders headers) {
        if(CollectionUtils.isEmpty(wellDtoList)){
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(wellDtoList, headers, HttpStatus.OK);
    }
}
