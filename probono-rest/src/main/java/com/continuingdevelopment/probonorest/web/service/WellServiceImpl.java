package com.continuingdevelopment.probonorest.web.service;

import com.continuingdevelopment.probonorest.web.dao.WellDao;
import com.continuingdevelopment.probonorest.web.model.WellDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class WellServiceImpl implements WellService{

    private final WellDao wellDao;

    public WellServiceImpl(WellDao wellDao){
        this.wellDao = wellDao;
    }

    @Override
    public String createWell(WellDto wellDto) {
        WellDto actual = wellDao.insert(wellDto);
        if(ObjectUtils.isEmpty(actual)){
            return null;
        }
        return actual.getId();
    }

    @Override
    public WellDto findWellById(String wellId) {
        return wellDao.findWellDtoById(wellId);
    }

    @Override
    public List<WellDto> findAllWells() {
        return wellDao.findAllByOrderByCountyAscTownshipAscWellNameAscWellNumberAsc();
    }

    @Override
    public List<WellDto> getWellsWhereWellNameContains(String wellName) {
        return wellDao.findWellDtosByWellNameRegexOrderByCountyAscTownshipAscWellNameAscWellNumberAsc(wellName);
    }

    @Override
    public List<WellDto> getWellsWhereCountyContains(String county) {
        return wellDao.findWellDtosByCountyRegexIgnoreCaseOrderByTownshipAscWellNameAscWellNumberAsc(county);
    }

    @Override
    public void updateWell(WellDto wellDto) {
        wellDao.save(wellDto);
    }

    @Override
    public void deleteWellById(String wellId) {
        wellDao.deleteWellDtoById(wellId);
    }

    @Override
    public List<WellDto> getWellsProducingBetween(Date fromDate, Date toDate) {
        return wellDao.findWellDtosByProductionPayedDateBetweenOrderByCountyAscTownshipAscWellNameAscWellNumberAsc(fromDate,toDate);
    }
}
