package com.continuingdevelopment.probonorest.web.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "camphire_drilling_wells")
@NoArgsConstructor
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

    public WellDto (String id, String apiNumber, String permitNumber, String wellName, String wellNumber,
                    String county, String township, List<ProductionDto> productionDtoList){
        this.id = id;
        this.apiNumber = apiNumber;
        this.permitNumber = permitNumber;
        this.wellName = wellName;
        this.wellNumber = wellNumber;
        this.county = county;
        this.township = township;
        this.production = productionDtoList;
        this.production.sort(new ProductionDtoComparator());
    }
}
