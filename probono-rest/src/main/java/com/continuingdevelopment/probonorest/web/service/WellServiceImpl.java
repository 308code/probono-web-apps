package com.continuingdevelopment.probonorest.web.service;

import com.continuingdevelopment.probonorest.web.dao.SongDao;
import com.continuingdevelopment.probonorest.web.dao.WellDao;
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
    public List<WellDto> findAllWells() {
        return wellDao.findAll();
    }
}
