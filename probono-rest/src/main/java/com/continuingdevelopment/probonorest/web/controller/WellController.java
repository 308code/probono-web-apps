package com.continuingdevelopment.probonorest.web.controller;

import com.continuingdevelopment.probonorest.web.model.WellDto;
import com.continuingdevelopment.probonorest.web.service.WellService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
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
        List<WellDto> songDtoList = wellService.findAllWells();
        HttpHeaders headers = addCountToHeader(songDtoList);
        return new ResponseEntity<>(songDtoList,headers,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WellDto> getWellById(@PathVariable("id") String id){
        WellDto wellDto = wellService.findWellById(id);
        return new ResponseEntity<>(wellDto,HttpStatus.OK);
    }

    @GetMapping("/name/contains/{wellName}")
    public ResponseEntity<List<WellDto>> getWellsWhereSongArtistContains(@PathVariable("wellName") String wellName){
        List<WellDto> wellDtoList = wellService.getWellsWhereWellNameContains(wellName);
        HttpHeaders headers = addCountToHeader(wellDtoList);
        return new ResponseEntity<>(wellDtoList, headers, HttpStatus.OK);
    }

    @GetMapping("/county/contains/{county}")
    public ResponseEntity<List<WellDto>> getWellsWhereCountyContains(@PathVariable("county") String county){
        List<WellDto> wellDtoList = wellService.getWellsWhereCountyContains(county);
        HttpHeaders headers = addCountToHeader(wellDtoList);
        return new ResponseEntity<>(wellDtoList, headers, HttpStatus.OK);
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
        if(CollectionUtils.isEmpty(wellDtoList)){
            wellDtoList = new ArrayList<>();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("count", String.valueOf(wellDtoList.size()));
        return headers;
    }
}
