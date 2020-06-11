package com.spintech.testtask.service.impl;

import com.spintech.testtask.entity.TVShow;
import com.spintech.testtask.entity.User;
import com.spintech.testtask.exceptions.EntityNotFoundException;
import com.spintech.testtask.infrastructure.enums.TVShowStatus;
import com.spintech.testtask.repository.TVShowRepository;
import com.spintech.testtask.service.TVShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TVShowServiceImpl implements TVShowService {

    @Autowired
    private TVShowRepository tvShowRepository;

    @Override
    public List<TVShow> findAllUserTVShow(Long userId) {
        return tvShowRepository.findAllTVShowByUser(userId);
    }

    @Override
    public TVShow addShow(User user, Long tvShowId,String originalTitle, TVShowStatus tvShowStatus) {
        if (tvShowRepository.findByIdAndUser(tvShowId, user.getId()).isPresent()) {
            return null;
        }
        TVShow tvShow = TVShow.builder().tmdbId(tvShowId).originalTitle(originalTitle).status(tvShowStatus).user(user).build();
        return tvShowRepository.save(tvShow);
    }

    @Override
    public TVShow updateShowStatus(User user, Long tvShowId,TVShowStatus tvShowStatus) {
        TVShow tvShow = tvShowRepository.findByIdAndUser(tvShowId, user.getId()).orElseThrow(() -> new EntityNotFoundException("TVShow for current user not found", tvShowId.toString()));
        tvShow.setStatus(tvShowStatus);
        return tvShowRepository.save(tvShow);
    }

    @Override
    public List<TVShow> findAllUnwatchedTVShow(Long userId) {
        return tvShowRepository.findAllByStatus(userId,TVShowStatus.WHACHED);
    }
}