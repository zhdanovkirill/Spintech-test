package com.spintech.testtask.controller;

import com.spintech.testtask.entity.User;
import com.spintech.testtask.service.UserService;
import com.spintech.testtask.service.tmdb.TmdbApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private UserService userService;

    @Autowired
    private TmdbApi tmdbApi;

    @RequestMapping(value = "/person", method = GET)
    public ResponseEntity findPersonByName(@RequestParam String personName) {

        String actors = tmdbApi.findPersonByName(personName);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(actors);
    }

    @RequestMapping(value = "/unwatchedShowWithFavoriteActors", method = GET)
    public ResponseEntity findUnwatchedShowWithFavoriteActors(@RequestParam String email,
                                                              @RequestParam String password) {

        User user = userService.findUser(email, password);
        String tvShow = tmdbApi.findUnwatchedShowWithFavoriteActors(user);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(tvShow);
    }

}
