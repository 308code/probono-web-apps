package com.continuingdevelopment.probonorest.web.service;

import com.continuingdevelopment.probonorest.web.model.WellDto;

import java.util.Date;
import java.util.List;

public interface WellService {
    WellDto findWellById(String wellId);
    List<WellDto> findAllWells();
//    List<WellDto> getWellNameContains(String title);
//    List<WellDto> getWellCountyContains(String title);
//    List<WellDto> getWellsThatProducedBetween(Date fromDate, Date toDate);
//    String createWell(WellDto wellDto);
//    void updateWell(WellDto wellDto);
//    void deleteWell(String songId);
}
