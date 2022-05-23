package com.continuingdevelopment.probonorest.web.controller;

import com.continuingdevelopment.probonorest.web.aspects.LogMethod;
import com.continuingdevelopment.probonorest.web.model.WellDto;
import com.continuingdevelopment.probonorest.web.service.WellService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @LogMethod(level = "INFO")
    @PostMapping()
    public ResponseEntity<String> createWell(@RequestBody WellDto wellDto){
        return wellService.createWell(wellDto);
    }

    @LogMethod(level = "INFO")
    @GetMapping("/{id}")
    public ResponseEntity<WellDto> getWellById(@PathVariable("id") String id){
        return wellService.getWellById(id);
    }

    @LogMethod(level = "INFO")
    @GetMapping()
    public ResponseEntity<List<WellDto>> getAllWells(){
        return wellService.getAllWells();
    }

    @LogMethod(level = "INFO")
    @GetMapping("/name/contains/{wellName}")
    public ResponseEntity<List<WellDto>> getWellsWhereWellNameContains(@PathVariable("wellName") String wellName){
        return wellService.getAllWellsByName(wellName);
    }

    @LogMethod(level = "INFO")
    @GetMapping("/county/contains/{county}")
    public ResponseEntity<List<WellDto>> getWellsWhereCountyContains(@PathVariable("county") String county){
        return wellService.getAllWellsByCounty(county);
    }

    @LogMethod(level = "INFO")
    @GetMapping("/date-played/between/{fromDate}/{toDate}")
    public ResponseEntity<List<WellDto>> getWellsWhereCountyContains(
            @PathVariable("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @PathVariable("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate){
        return wellService.getAllWellsProducingBetween(fromDate,toDate);
    }

    @LogMethod(level = "INFO")
    @PutMapping()
    public ResponseEntity<Void> updateWell(@RequestBody WellDto wellDto){
        return wellService.updateWell(wellDto);
    }

    @LogMethod(level = "INFO")
    @DeleteMapping("/{wellId}")
    public ResponseEntity<Void> deleteWellById(@PathVariable("wellId") String id){
        return wellService.deleteWellById(id);
    }


}
