package com.continuingdevelopment.probonorest.web.service;

import com.continuingdevelopment.probonorest.web.dao.SongDao;
import com.continuingdevelopment.probonorest.web.dao.WellDao;
import com.continuingdevelopment.probonorest.web.model.PlayedDto;
import com.continuingdevelopment.probonorest.web.model.ProductionDto;
import com.continuingdevelopment.probonorest.web.model.SongDto;
import com.continuingdevelopment.probonorest.web.model.WellDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        return actual.getId();
    }

    @Override
    public WellDto findWellById(String wellId) {
        return wellDao.findWellDtoById(wellId);
    }

    @Override
    public List<WellDto> findAllWells() {
        return wellDao.findAll();
    }

    @Override
    public List<WellDto> getWellsWhereWellNameContains(String wellName) {
        return wellDao.findWellDtosByWellNameContains(wellName);
    }

    @Override
    public List<WellDto> getWellsWhereCountyContains(String county) {
        return wellDao.findWellDtosByCountyContains(county);
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
    public List<WellDto> getAllWellsByProductionDateRange(Date fromDate, Date toDate) {
        List<WellDto> originalListOfWellDtos = wellDao.findAll();
        List<WellDto> resultingDtoList = new ArrayList<>();
        originalListOfWellDtos.forEach(wellDto -> {
            List<ProductionDto> tempProductionDtoList = wellDto.getProduction();
            for (ProductionDto productionDto : tempProductionDtoList) {
                if (!fromDate.after(productionDto.getPayedDate()) && !toDate.before(productionDto.getPayedDate())) {
                    resultingDtoList.add(wellDto);
                    break;
                }
            }
        });
        return resultingDtoList;
    }
}
