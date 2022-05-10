package com.continuingdevelopment.probonorest.web.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "flcSongs")
@NoArgsConstructor
@Builder
public class SongDto {
    @Id
    private String id;
    private String title;
    private String artist;
    private String[] aka;
    private List<PlayedDto> played;

    public SongDto(String id, String title, String artist, String[] aka,List<PlayedDto> playedDtoList){
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.aka = aka;
        this.played = playedDtoList;
        this.played.sort(new PlayedDtoComparator());
    }
}
