package com.continuingdevelopment.probonorest.web.model;

import java.util.Comparator;

public class ProductionDtoComparator implements Comparator<ProductionDto> {

    @Override
    public int compare(ProductionDto productionDtoOne, ProductionDto productionDtoTwo) {
        return productionDtoOne.getPayedDate().compareTo(productionDtoTwo.getPayedDate());
    }
}
