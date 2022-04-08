package com.continuingdevelopment.probonorest.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "flcSongs")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongDto {
    @Id
    private String id;
    private String title;
    private String artist;
    private String[] aka;
    private List<PlayedDto> played;
}
