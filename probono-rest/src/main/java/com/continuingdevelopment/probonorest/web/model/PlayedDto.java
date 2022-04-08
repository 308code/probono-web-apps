package com.continuingdevelopment.probonorest.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayedDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date datePlayed;
    private String notes;
    private String series;
}
