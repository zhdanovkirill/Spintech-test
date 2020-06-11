package com.spintech.testtask.service.tmdb.impl;

import com.spintech.testtask.entity.Actor;
import com.spintech.testtask.entity.TVShow;
import com.spintech.testtask.entity.User;
import com.spintech.testtask.service.ActorService;
import com.spintech.testtask.service.TVShowService;
import com.spintech.testtask.service.tmdb.TmdbApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TmdbApiImpl implements TmdbApi {
    @Value("${tmdb.apikey}")
    private String tmdbApiKey;
    @Value("${tmdb.language}")
    private String tmdbLanguage;
    @Value("${tmdb.api.base.url}")
    private String tmdbApiBaseUrl;

    @Autowired
    private ActorService actorService;
    @Autowired
    private TVShowService tvShowService;

    public String popularTVShows() throws IllegalArgumentException {
        try {
            String url = getTmdbUrl("/tv/popular");
            return getTmdbResponseBody(url);
        } catch (URISyntaxException e) {
            log.error("Couldn't get popular tv shows");
        }
        return null;
    }

    public String popularPerson() throws IllegalArgumentException {
        try {
            String url = getTmdbUrl("/person/popular");
            return getTmdbResponseBody(url);
        } catch (URISyntaxException e) {
            log.error("Couldn't get popular person");
        }
        return null;
    }

    public String findPersonByName(String personName) throws IllegalArgumentException {
        try {
            String url = addUrlParameter(getTmdbUrl("query"), "/search/person", personName);
            return getTmdbResponseBody(url);
        } catch (URISyntaxException e) {
            log.error("Couldn't find person");
        }
        return null;
    }

    @Override
    public String findTVShowById(Long tvShowId) {
        try {
            String url = getTmdbUrl(String.format("/tv/%s", tvShowId));
            return getTmdbResponseBody(url);
        } catch (URISyntaxException e) {
            log.error("Couldn't find person");
        }
        return null;
    }

    @Override
    public String findUnwatchedShowWithFavoriteActors(User user) {
        List<Actor> actors = actorService.getAllFavoriteActor(user);
        List<TVShow> tvShows = tvShowService.findAllUnwatchedTVShow(user.getId());
        String favoriteActors = actors.stream().map(e -> e.getTmdbId().toString()).collect(Collectors.joining("|"));
        String movies = "";
        try {
            String url = addUrlParameter(getTmdbUrl("/discover/movie"), "with_people", favoriteActors);
            movies = getTmdbResponseBody(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return movies;
    }

    private String getTmdbResponseBody(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response
                = restTemplate.getForEntity(url, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            return null;
        }

        return response.getBody();
    }

    private String addUrlParameter(String url , String queryParamName, String queryParam) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(url);
        uriBuilder.addParameter(queryParamName, queryParam);
        return uriBuilder.build().toString();
    }

    private String getTmdbUrl(String tmdbItem) throws URISyntaxException {
        StringBuilder builder = new StringBuilder(tmdbApiBaseUrl);
        builder.append(tmdbItem);
        URIBuilder uriBuilder = new URIBuilder(builder.toString());
        uriBuilder.addParameter("language", tmdbLanguage);
        uriBuilder.addParameter("api_key", tmdbApiKey);
        return uriBuilder.build().toString();
    }
}
