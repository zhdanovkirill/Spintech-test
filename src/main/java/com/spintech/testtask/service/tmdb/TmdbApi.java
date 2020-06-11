package com.spintech.testtask.service.tmdb;

import com.spintech.testtask.entity.User;

public interface TmdbApi {
    String popularTVShows();
    String popularPerson();
    String findPersonByName(String personName);
    String findTVShowById(Long tvShowId);
    String findUnwatchedShowWithFavoriteActors(User user);
}
