package com.continuingdevelopment.probonorest.web.model;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Comparator;

public class SongDtoComparator implements Comparator<SongDto> {
    @Override
    public int compare(SongDto songDtoOne, SongDto songDtoTwo) {
        if(ObjectUtils.isEmpty(songDtoOne) || ObjectUtils.isEmpty(songDtoTwo) ||
                CollectionUtils.isEmpty(songDtoOne.getPlayed()) || CollectionUtils.isEmpty(songDtoTwo.getPlayed())){
            return 0;
        }
        return songDtoOne.getPlayed().get(songDtoOne.getPlayed().size()-1).getDatePlayed()
                .compareTo(songDtoTwo.getPlayed().get(songDtoTwo.getPlayed().size()-1).getDatePlayed());
    }
}
