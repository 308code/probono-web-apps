package com.continuingdevelopment.probonorest.web.service;

import com.continuingdevelopment.probonorest.web.model.WellDto;

import java.util.Date;
import java.util.List;

public interface WellService {
    WellDto findWellById(String wellId);
    List<WellDto> findAllWells();
    List<WellDto> getWellsWhereWellNameContains(String title);
    List<WellDto> getWellsWhereCountyContains(String title);
    List<WellDto> getWellsProducingBetween(Date fromDate, Date toDate);
    String createWell(WellDto wellDto);
    void updateWell(WellDto wellDto);
    void deleteWellById(String wellId);
}
