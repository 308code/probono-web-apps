package com.continuingdevelopment.probonorest.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "camphire_drilling_wells")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WellDto {
    @Id
    private String id;
    private String apiNumber;
    private String permitNumber;
    private String wellName;
    private String wellNumber;
    private String county;
    private String township;
    private List<ProductionDto> production;
}
