package com.spintech.testtask.service;

import com.spintech.testtask.entity.TVShow;
import com.spintech.testtask.entity.User;
import com.spintech.testtask.infrastructure.enums.TVShowStatus;

import java.util.List;

public interface TVShowService {
    List<TVShow> findAllUserTVShow(Long userId);
    TVShow addShow(User id, Long tvShowId,String originalTitle, TVShowStatus tvShowStatus);
    TVShow updateShowStatus(User user, Long tvShowId, TVShowStatus tvShowStatus);
    List<TVShow> findAllUnwatchedTVShow(Long userId);
}

