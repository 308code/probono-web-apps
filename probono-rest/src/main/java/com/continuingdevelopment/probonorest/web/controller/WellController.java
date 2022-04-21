package com.continuingdevelopment.probonorest.web.controller;

import com.continuingdevelopment.probonorest.web.model.WellDto;
import com.continuingdevelopment.probonorest.web.service.WellService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
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

    private HttpHeaders addCountToHeader(List<WellDto> wellDtoList){
        if(CollectionUtils.isEmpty(wellDtoList)){
            wellDtoList = new ArrayList<>();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("count", String.valueOf(wellDtoList.size()));
        return headers;
    }
}
