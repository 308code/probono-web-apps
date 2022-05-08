package com.continuingdevelopment.probonorest.web.controller;

import com.continuingdevelopment.probonorest.web.model.WellDto;
import com.continuingdevelopment.probonorest.web.service.WellService;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/wells")
public class WellController {

    private final WellService wellService;

    public WellController(WellService wellService){
        this.wellService = wellService;
    }
    
    @PostMapping()
    public ResponseEntity<String> createWell(@RequestBody WellDto wellDto){
        String newWellId = wellService.createWell(wellDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("location","http://localhost:8080/api/wells/" + newWellId);
        return new ResponseEntity<>(newWellId,headers,HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<WellDto>> getAllWells(){
        List<WellDto> wellDtoList = wellService.findAllWells();
        HttpHeaders headers = addCountToHeader(wellDtoList);
        return getListResponseEntity(wellDtoList, headers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WellDto> getWellById(@PathVariable("id") String id){
        WellDto wellDto = wellService.findWellById(id);
        HttpHeaders headers = addCountToHeader(wellDto);
        if(ObjectUtils.isEmpty(wellDto)){
            return new ResponseEntity<>(headers,HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(wellDto,headers,HttpStatus.OK);
    }

    @GetMapping("/name/contains/{wellName}")
    public ResponseEntity<List<WellDto>> getWellsWhereWellNameContains(@PathVariable("wellName") String wellName){
        List<WellDto> wellDtoList = wellService.getWellsWhereWellNameContains(wellName);
        HttpHeaders headers = addCountToHeader(wellDtoList);
        return getListResponseEntity(wellDtoList, headers);
    }


    @GetMapping("/county/contains/{county}")
    public ResponseEntity<List<WellDto>> getWellsWhereCountyContains(@PathVariable("county") String county){
        List<WellDto> wellDtoList = wellService.getWellsWhereCountyContains(county);
        HttpHeaders headers = addCountToHeader(wellDtoList);
        return getListResponseEntity(wellDtoList, headers);
    }

    @GetMapping("/date-played/between/{fromDate}/{toDate}")
    public ResponseEntity<List<WellDto>> getWellsWhereCountyContains(
            @PathVariable("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @PathVariable("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate){
        List<WellDto> wellDtoList = wellService.getWellsProducingBetween(fromDate,toDate);
        HttpHeaders headers = addCountToHeader(wellDtoList);
        return getListResponseEntity(wellDtoList, headers);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateWell(@RequestBody WellDto wellDto){
        wellService.updateWell(wellDto);
    }

    @DeleteMapping("/{wellId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWellById(@PathVariable("wellId") String id){
        wellService.deleteWellById(id);
    }

    private HttpHeaders addCountToHeader(List<WellDto> wellDtoList){
        HttpHeaders headers = new HttpHeaders();

        if(null == wellDtoList){
            wellDtoList = new ArrayList<>(0);
        }
        headers.add("count", String.valueOf(wellDtoList.size()));
        return headers;
    }

    private HttpHeaders addCountToHeader(WellDto wellDto){
        HttpHeaders headers = new HttpHeaders();

        if(null == wellDto){
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
