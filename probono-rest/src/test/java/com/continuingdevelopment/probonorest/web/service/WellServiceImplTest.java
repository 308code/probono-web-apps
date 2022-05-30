package com.continuingdevelopment.probonorest.web.service;

import com.continuingdevelopment.probonorest.web.dao.WellDao;
import com.continuingdevelopment.probonorest.web.model.ProductionDto;
import com.continuingdevelopment.probonorest.web.model.WellDto;
import com.flextrade.jfixture.JFixture;
import com.mongodb.MongoException;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WellServiceImplTest {
    @Mock
    WellDao mockWellDao;
    WellService wellService;
    JFixture jFixture = new JFixture();
    List<WellDto> wellDtoList;
    List<ProductionDto> productionDtoList;
    WellDto wellDto1;
    WellDto wellDto2;
    WellDto wellDto3;
    ProductionDto productionDto1;
    ProductionDto productionDto2;
    ProductionDto productionDto3;

    @BeforeEach
    void setUp() {
        wellService = new WellServiceImpl(mockWellDao);
        wellDtoList = Lists.newArrayList(jFixture.collections().createCollection(WellDto.class));
        productionDtoList = Lists.newArrayList(jFixture.collections().createCollection(ProductionDto.class));
        wellDto1 = jFixture.create(WellDto.class);
        wellDto2 = jFixture.create(WellDto.class);
        wellDto3 = jFixture.create(WellDto.class);
        productionDto1 = jFixture.create(ProductionDto.class);
        productionDto2 = jFixture.create(ProductionDto.class);
        productionDto3 = jFixture.create(ProductionDto.class);
    }

    @AfterEach
    void tearDown() {
        productionDto1 = null;
        productionDto2 = null;
        productionDto3 = null;
        wellDto1 = null;
        wellDto2 = null;
        wellDto3 = null;
        wellDtoList = null;
        productionDtoList = null;
    }
    @Test
    void createWell() {
        when(mockWellDao.insert(wellDto1)).thenReturn(wellDto1);
        ResponseEntity<String> responseEntity = wellService.createWell(wellDto1);
        assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
    }

    @Test
    void createWell_Throws_Exception() {
        when(mockWellDao.insert(wellDto1)).thenThrow(new MongoException("Mongo Exception Thrown!"));
        ResponseEntity<String> responseEntity = wellService.createWell(wellDto1);
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
    }

    @Test
    void createWell_returning_null() {
        when(mockWellDao.insert(wellDto1)).thenReturn(null);
        ResponseEntity<String> responseEntity = wellService.createWell(wellDto1);
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
    }
    @Test
    void createWell_returning_Well_With_Empty_Id() {
        wellDto1.setId("");
        when(mockWellDao.insert(wellDto1)).thenReturn(wellDto1);
        ResponseEntity<String> responseEntity = wellService.createWell(wellDto1);
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
    }
    
    @Test
    void getWellById() {
        when(mockWellDao.findWellDtoById(wellDto2.getId())).thenReturn(wellDto2);
        ResponseEntity<WellDto> responseEntity = wellService.getWellById(wellDto2.getId());
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(1, Integer.valueOf(Objects.requireNonNull(responseEntity.getHeaders().get("count")).get(0)));
    }

    @Test
    void getWellById_ThrowsMongoException() {
        when(mockWellDao.findWellDtoById(wellDto3.getId())).thenThrow(new MongoException("Mongo Exception Thrown!"));
        ResponseEntity<WellDto> responseEntity = wellService.getWellById(wellDto3.getId());
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
    }
    @Test
    void getWellById_NoExceptionThrownButReturnsNull() {
        when(mockWellDao.findWellDtoById(wellDto2.getId())).thenReturn(null);
        ResponseEntity<WellDto> responseEntity = wellService.getWellById(wellDto2.getId());
        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
        assertEquals(0, Integer.valueOf(Objects.requireNonNull(responseEntity.getHeaders().get("count")).get(0)));
    }
    @Test
    void getAllWells() {
        when(mockWellDao.findAllByOrderByCountyAscTownshipAscWellNameAscWellNumberAsc()).thenReturn(wellDtoList);
        ResponseEntity<List<WellDto>> responseEntity = wellService.getAllWells();
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(3, Integer.valueOf(Objects.requireNonNull(
                responseEntity.getHeaders().get("count")).get(0)));
    }

    @Test
    void getAllWells_Throws_Exception() {
        when(mockWellDao.findAllByOrderByCountyAscTownshipAscWellNameAscWellNumberAsc()).thenThrow(
                new MongoException("Threw a MongoException!"));
        ResponseEntity<List<WellDto>> responseEntity = wellService.getAllWells();
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
        assertNull(responseEntity.getHeaders().get("count"));
    }

    @Test
    void getAllWells_Nothing_Returned() {
        when(mockWellDao.findAllByOrderByCountyAscTownshipAscWellNameAscWellNumberAsc()).thenReturn(null);
        ResponseEntity<List<WellDto>> responseEntity = wellService.getAllWells();
        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
        assertEquals(0,Integer.valueOf(Objects.requireNonNull(
                responseEntity.getHeaders().get("count")).get(0)).intValue());
    }

    @Test
    void getAllWellsByWellName() {
        when(mockWellDao.findWellDtosByWellNameRegexOrderByCountyAscTownshipAscWellNameAscWellNumberAsc(
                anyString())).thenReturn(wellDtoList);
        ResponseEntity<List<WellDto>> responseEntity = wellService.getAllWellsByName("");
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }

    @Test
    void getAllWellsByWellNameReturnsNull() {
        when(mockWellDao.findWellDtosByWellNameRegexOrderByCountyAscTownshipAscWellNameAscWellNumberAsc(
                anyString())).thenReturn(null);
        ResponseEntity<List<WellDto>> responseEntity = wellService.getAllWellsByName("");
        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }

    @Test
    void getAllWellsByName_throws_exception() {
        when(mockWellDao.findWellDtosByWellNameRegexOrderByCountyAscTownshipAscWellNameAscWellNumberAsc(
                anyString())).thenThrow(new MongoException("Mongo Exception was thrown!"));
        ResponseEntity<List<WellDto>> responseEntity = wellService.getAllWellsByName("");
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
        assertNull(responseEntity.getHeaders().get("count"));
    }

    @Test
    void getAllWellsByWellCounty() {
        when(mockWellDao.findWellDtosByCountyRegexIgnoreCaseOrderByTownshipAscWellNameAscWellNumberAsc(
                anyString())).thenReturn(wellDtoList);
        ResponseEntity<List<WellDto>> responseEntity = wellService.getAllWellsByCounty("");
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }

    @Test
    void getAllWellsByWellCountyReturnsNull() {
        when(mockWellDao.findWellDtosByCountyRegexIgnoreCaseOrderByTownshipAscWellNameAscWellNumberAsc(
                anyString())).thenReturn(null);
        ResponseEntity<List<WellDto>> responseEntity = wellService.getAllWellsByCounty("");
        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }

    @Test
    void getAllWellsByCounty_throws_exception() {
        when(mockWellDao.findWellDtosByCountyRegexIgnoreCaseOrderByTownshipAscWellNameAscWellNumberAsc(
                anyString())).thenThrow(new MongoException("Mongo Exception was thrown!"));
        ResponseEntity<List<WellDto>> responseEntity = wellService.getAllWellsByCounty("");
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
        assertNull(responseEntity.getHeaders().get("count"));
    }

    @Test
    void getAllWellsPlayedBetween() {
        when(mockWellDao.findWellDtosByProductionPayedDateBetweenOrderByCountyAscTownshipAscWellNameAscWellNumberAsc(
                any(Date.class), any(Date.class))).thenReturn(wellDtoList);
        ResponseEntity<List<WellDto>> responseEntity = wellService.getAllWellsProducingBetween(new Date(), new Date());
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }

    @Test
    void getAllWellsPlayedBetweenReturnsNull() {
        when(mockWellDao.findWellDtosByProductionPayedDateBetweenOrderByCountyAscTownshipAscWellNameAscWellNumberAsc(
                any(Date.class), any(Date.class))).thenReturn(null);
        ResponseEntity<List<WellDto>> responseEntity = wellService.getAllWellsProducingBetween(new Date(), new Date());
        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }

    @Test
    void getAllWellsPlayedBetween_throws_exception() {
        when(mockWellDao.findWellDtosByProductionPayedDateBetweenOrderByCountyAscTownshipAscWellNameAscWellNumberAsc(
                any(Date.class), any(Date.class))).thenThrow(new MongoException("Threw a MongoException!"));
        ResponseEntity<List<WellDto>> responseEntity = wellService.getAllWellsProducingBetween(new Date(), new Date());
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
        assertNull(responseEntity.getHeaders().get("count"));
    }
    @Test
    void updateWell() {
        when(mockWellDao.save(wellDto1)).thenReturn(wellDto1);
        ResponseEntity<Void> responseEntity = wellService.updateWell(wellDto1);
        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }

    @Test
    void updateWell_throws_exception() {
        when(mockWellDao.save(wellDto1)).thenThrow(new MongoException("Mongo Exception was thrown!"));
        ResponseEntity<Void> responseEntity = wellService.updateWell(wellDto1);
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
    }

    @Test
    void deleteWell() {
        doNothing().when(mockWellDao).deleteWellDtoById(anyString());
        ResponseEntity<Void> responseEntity = wellService.deleteWellById("someWellId");
        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }

    @Test
    void deleteWell_throws_exception() {
        doThrow(new MongoException("Mongo Exception was thrown!")).when(mockWellDao).deleteWellDtoById("someWellId");
        ResponseEntity<Void> responseEntity = wellService.deleteWellById("someWellId");
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
    }
}