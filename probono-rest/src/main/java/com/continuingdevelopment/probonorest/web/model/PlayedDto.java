package com.continuingdevelopment.probonorest.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayedDto {
    private Date datePlayed;
    private String notes;
    private String series;
}
