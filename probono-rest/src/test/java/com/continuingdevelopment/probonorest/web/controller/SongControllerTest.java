package com.continuingdevelopment.probonorest.web.controller;

import com.continuingdevelopment.probonorest.web.dao.SongDao;
import com.continuingdevelopment.probonorest.web.model.SongDto;
import com.continuingdevelopment.probonorest.web.service.SongService;
import com.continuingdevelopment.probonorest.web.service.SongServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

@WebMvcTest
class SongControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    SongService mockSongService;



    @Test
    void getSong() throws Exception {
        mockMvc.perform(get("/api/v1/song/"+ UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void createSong() throws Exception {
        SongDto songDto = SongDto.builder().id(UUID.randomUUID()).title("Hello").artist("World").build();
        Mockito.when(mockSongService.createSong(Mockito.any(SongDto.class))).thenReturn(songDto.getId());
        String songDtoJson = objectMapper.writeValueAsString(songDto);
        mockMvc.perform(post("/api/v1/song")
                .contentType(MediaType.APPLICATION_JSON)
                .content(songDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void updateSong() throws Exception {
        UUID uuid = UUID.randomUUID();
        SongDto songDto = SongDto.builder().id(uuid).title("Hello").artist("World").build();
        Mockito.doNothing().when(mockSongService).updateSong(Mockito.any(UUID.class), Mockito.any(SongDto.class));
        String songDtoJson = objectMapper.writeValueAsString(songDto);
        mockMvc.perform(put("/api/v1/song/" + uuid.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(songDtoJson))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteSong() throws Exception {
        mockMvc.perform(delete("/api/v1/song/"+ UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
    }
}