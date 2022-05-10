package com.continuingdevelopment.probonorest.web.service;

import com.continuingdevelopment.probonorest.web.dao.WellDao;
import com.continuingdevelopment.probonorest.web.model.WellDto;
import com.continuingdevelopment.probonorest.web.model.WellDtoComparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class WellServiceImpl implements WellService{

    private final WellDao wellDao;
    private final WellDtoComparator wellDtoComparator = new WellDtoComparator();

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
        List<WellDto> wellDtoList = wellDao.findAllByOrderByCountyAscTownshipAscWellNameAscWellNumberAsc();
        wellDtoList.sort(wellDtoComparator);
        return wellDtoList;
    }

    @Override
    public List<WellDto> getWellsWhereWellNameContains(String wellName) {
        List<WellDto> wellDtoList =  wellDao.findWellDtosByWellNameRegexOrderByCountyAscTownshipAscWellNameAscWellNumberAsc(wellName);
        wellDtoList.sort(wellDtoComparator);
        return wellDtoList;
    }

    @Override
    public List<WellDto> getWellsWhereCountyContains(String county) {
        List<WellDto> wellDtoList = wellDao.findWellDtosByCountyRegexIgnoreCaseOrderByTownshipAscWellNameAscWellNumberAsc(county);
        wellDtoList.sort(wellDtoComparator);
        return wellDtoList;
    }

    @Override
    public List<WellDto> getWellsProducingBetween(Date fromDate, Date toDate) {
        List<WellDto> wellDtoList =  wellDao.findWellDtosByProductionPayedDateBetweenOrderByCountyAscTownshipAscWellNameAscWellNumberAsc(fromDate,toDate);
        wellDtoList.sort(wellDtoComparator);
        return wellDtoList;
    }

    @Override
    public void updateWell(WellDto wellDto) {
        wellDao.save(wellDto);
    }

    @Override
    public void deleteWellById(String wellId) {
        wellDao.deleteWellDtoById(wellId);
    }
}
