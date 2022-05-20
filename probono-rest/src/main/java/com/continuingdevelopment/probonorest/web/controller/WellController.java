package com.continuingdevelopment.probonorest.web.controller;

import com.continuingdevelopment.probonorest.web.aspects.LogMethod;
import com.continuingdevelopment.probonorest.web.model.WellDto;
import com.continuingdevelopment.probonorest.web.service.WellService;
import com.mongodb.MongoException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/wells")
public class WellController {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    private final WellService wellService;

    public WellController(WellService wellService){
        this.wellService = wellService;
    }

    @LogMethod(level = "INFO")
    @PostMapping()
    public ResponseEntity<String> createWell(@RequestBody WellDto wellDto){
        String newWellId;
        try {
            newWellId = wellService.createWell(wellDto);
        }catch (MongoException e){
            log.error("Error creating well: {} MESSAGE: {}  STACKTRACE: {}", wellDto, e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(StringUtils.isEmpty(newWellId)) {
            return new ResponseEntity<>("Create Failed!",HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("location", "http://localhost:8080/api/wells/" + newWellId);
        return new ResponseEntity<>(newWellId, headers, HttpStatus.CREATED);

    }
    @LogMethod(level = "INFO")
    @GetMapping("/{id}")
    public ResponseEntity<WellDto> getWellById(@PathVariable("id") String id){
        WellDto wellDto;
        try {
            wellDto = wellService.findWellById(id);
        }catch (MongoException e){
            log.error("Error getting well with id: {} MESSAGE: {}  STACKTRACE: {}", id, e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(ObjectUtils.isEmpty(wellDto)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        HttpHeaders headers = addCountToHeader(wellDto);
        return new ResponseEntity<>(wellDto,headers,HttpStatus.OK);
    }

    @LogMethod(level = "INFO")
    @GetMapping()
    public ResponseEntity<List<WellDto>> getAllWells(){
        List<WellDto> wellDtoList;
        try {
            wellDtoList = wellService.findAllWells();
        }catch (MongoException e){
            log.error("Error getting all wells: MESSAGE: {}  STACKTRACE: {}", e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(CollectionUtils.isEmpty(wellDtoList)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        HttpHeaders headers = addCountToHeader(wellDtoList);
        return getListResponseEntity(wellDtoList, headers);
    }

    @LogMethod(level = "INFO")
    @GetMapping("/name/contains/{wellName}")
    public ResponseEntity<List<WellDto>> getWellsWhereWellNameContains(@PathVariable("wellName") String wellName){
        List<WellDto> wellDtoList;
        try {
            wellDtoList = wellService.getWellsWhereWellNameContains(wellName);
        }catch (MongoException e){
            log.error("Error getting all wells who's name contains: {} MESSAGE: {}  STACKTRACE: {}",wellName, e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(CollectionUtils.isEmpty(wellDtoList)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        HttpHeaders headers = addCountToHeader(wellDtoList);
        return getListResponseEntity(wellDtoList, headers);
    }

    @LogMethod(level = "INFO")
    @GetMapping("/county/contains/{county}")
    public ResponseEntity<List<WellDto>> getWellsWhereCountyContains(@PathVariable("county") String county){
        List<WellDto> wellDtoList;
        try {
            wellDtoList = wellService.getWellsWhereCountyContains(county);
        }catch (MongoException e){
            log.error("Error getting all wells who's county name contains: {} MESSAGE: {}  STACKTRACE: {}",county, e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(CollectionUtils.isEmpty(wellDtoList)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        HttpHeaders headers = addCountToHeader(wellDtoList);
        return getListResponseEntity(wellDtoList, headers);
    }

    @LogMethod(level = "INFO")
    @GetMapping("/date-played/between/{fromDate}/{toDate}")
    public ResponseEntity<List<WellDto>> getWellsWhereCountyContains(
            @PathVariable("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @PathVariable("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate){
        List<WellDto> wellDtoList;
        try {
            wellDtoList = wellService.getWellsProducingBetween(fromDate, toDate);
        }catch (MongoException e){
            log.error("Error getting all wells that contain production between: {} and {} MESSAGE: {}  STACKTRACE: {}",
                    SDF.format(fromDate), SDF.format(toDate), e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(CollectionUtils.isEmpty(wellDtoList)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        HttpHeaders headers = addCountToHeader(wellDtoList);
        return getListResponseEntity(wellDtoList, headers);
    }

    @LogMethod(level = "INFO")
    @PutMapping()
    public ResponseEntity<Void> updateWell(@RequestBody WellDto wellDto){
        try {
            wellService.updateWell(wellDto);
        }catch (MongoException e){
            log.error("Error Updating well: {}  MESSAGE: {}  STACKTRACE: {}" , wellDto, e.getMessage(), e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @LogMethod(level = "INFO")
    @DeleteMapping("/{wellId}")
    public ResponseEntity<Void> deleteWellById(@PathVariable("wellId") String id){
        try {
            wellService.deleteWellById(id);
        }catch (MongoException e){
            log.error("Error Deleting well with id: {}  MESSAGE: {}  STACKTRACE: {}" , id, e.getMessage(), e.getStackTrace());
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
