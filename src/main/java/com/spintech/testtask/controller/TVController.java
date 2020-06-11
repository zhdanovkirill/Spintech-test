package com.spintech.testtask.controller;

import com.spintech.testtask.entity.TVShow;
import com.spintech.testtask.entity.User;
import com.spintech.testtask.infrastructure.enums.TVShowStatus;
import com.spintech.testtask.service.TVShowService;
import com.spintech.testtask.service.UserService;
import com.spintech.testtask.service.tmdb.TmdbApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/tv")
public class TVController {
    @Autowired
    private UserService userService;

    @Autowired
    private TVShowService tvShowService;

    @Autowired
    private TmdbApi tmdbApi;

    @RequestMapping(value = "/popular", method = POST)
    public ResponseEntity popular(@RequestParam String email,
                                  @RequestParam String password) {
        if (userService.findUser(email, password) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String popularMovies = tmdbApi.popularTVShows();

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(popularMovies);
    }

    @RequestMapping(value = "/tvShow", method = GET)
    public ResponseEntity findTVShowById(@RequestParam String email,
                                         @RequestParam String password,
                                         @RequestParam Long tvShowId) {
        User currentUser = userService.findUser(email, password);
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        String tvShow = tmdbApi.findTVShowById(tvShowId);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(tvShow);
    }

    @RequestMapping(value = "/userTVShow", method = GET)
    public ResponseEntity findUserTVShow(@RequestParam String email,
                                         @RequestParam String password) {
        User currentUser = userService.findUser(email, password);
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        List<TVShow> tvShow = tvShowService.findAllUserTVShow(currentUser.getId());
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(tvShow);
    }

    @RequestMapping(value = "/tvShow", method = POST)
    public ResponseEntity addTVShow(@RequestParam String email,
                                    @RequestParam String password,
                                    @RequestParam Long tvShowId,
                                    @RequestParam String originalTitle,
                                    @RequestParam TVShowStatus tvShowStatus) {
        User user = userService.findUser(email, password);
        if (user != null) {
            TVShow tvShow = tvShowService.addShow(user, tvShowId, originalTitle, tvShowStatus);
            if (Objects.nonNull(tvShow)) {
                return ResponseEntity.status(HttpStatus.OK).body(tvShow);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("TV show already exist for current user");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad credentials");
        }
    }

    @RequestMapping(value = "/tvShowStatus", method = PUT)
    public ResponseEntity updateTVShowStatus(@RequestParam String email,
                                             @RequestParam String password,
                                             @RequestParam Long tvShowId,
                                             @RequestParam TVShowStatus tvShowStatus) {
        User user = userService.findUser(email, password);
        if (user != null) {
            TVShow tvShow = tvShowService.updateShowStatus(user, tvShowId, tvShowStatus);
            if (Objects.nonNull(tvShow)) {
                return ResponseEntity.status(HttpStatus.OK).body(tvShow);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("TV show already exist for current user");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad credentials");
        }
    }
}
