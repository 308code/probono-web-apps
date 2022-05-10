package com.continuingdevelopment.probonorest.web.model;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Comparator;

public class WellDtoComparator implements Comparator<WellDto> {

    @Override
    public int compare(WellDto wellDtoOne, WellDto wellDtoTwo) {
        if(ObjectUtils.isEmpty(wellDtoOne) || ObjectUtils.isEmpty(wellDtoTwo) ||
                CollectionUtils.isEmpty(wellDtoOne.getProduction()) || CollectionUtils.isEmpty(wellDtoTwo.getProduction())){
            return 0;
        }
        return wellDtoOne.getProduction().get(wellDtoOne.getProduction().size()-1).getPayedDate()
                .compareTo(wellDtoTwo.getProduction().get(wellDtoTwo.getProduction().size()-1).getPayedDate());
    }
}
