package com.continuingdevelopment.probonorest.web.service;

import com.continuingdevelopment.probonorest.web.dao.SongDao;
import com.continuingdevelopment.probonorest.web.dao.WellDao;
import com.continuingdevelopment.probonorest.web.model.SongDto;
import com.continuingdevelopment.probonorest.web.model.WellDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class WellServiceImpl implements WellService{

    private WellDao wellDao;

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
    public void updateWell(WellDto wellDto) {
        wellDao.save(wellDto);
    }

    @Override
    public void deleteWellById(String wellId) {
        wellDao.deleteWellDtoById(wellId);
    }
}
