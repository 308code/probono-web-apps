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
public class ProductionDto {
    private String type;
    private double quantity;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date payedDate;
}
