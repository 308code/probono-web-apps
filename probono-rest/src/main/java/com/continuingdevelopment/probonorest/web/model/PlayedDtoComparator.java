package com.continuingdevelopment.probonorest.web.model;

import org.springframework.util.ObjectUtils;

import java.util.Comparator;

public class PlayedDtoComparator implements Comparator<PlayedDto> {

    @Override
    public int compare(PlayedDto playedDtoOne, PlayedDto playedDtoTwo) {
        if(ObjectUtils.isEmpty(playedDtoOne) || ObjectUtils.isEmpty(playedDtoTwo)){
            return 0;
        }
        return playedDtoOne.getDatePlayed().compareTo(playedDtoTwo.getDatePlayed());
    }
}
