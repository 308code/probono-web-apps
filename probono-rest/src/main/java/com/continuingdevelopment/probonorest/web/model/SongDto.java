package com.continuingdevelopment.probonorest.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongDto {
    private UUID id;
    private String title;
    private String artist;
    private String[] aka;
    private List<PlayedDto> played;
}
