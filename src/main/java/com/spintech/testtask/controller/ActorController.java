package com.spintech.testtask.controller;

import com.spintech.testtask.entity.Actor;
import com.spintech.testtask.entity.User;
import com.spintech.testtask.service.ActorService;
import com.spintech.testtask.service.UserService;
import com.spintech.testtask.service.tmdb.TmdbApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/actor")
public class ActorController {
    @Autowired
    private UserService userService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private TmdbApi tmdbApi;

    @RequestMapping(value = "/popular", method = GET)
    public ResponseEntity popular(@RequestParam String email,
                                  @RequestParam String password) {
        if (userService.findUser(email, password) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String popularPerson = tmdbApi.popularPerson();

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(popularPerson);
    }

    @RequestMapping(value = "/", method = POST)
    public ResponseEntity addFavoriteActor(@RequestParam String email,
                                           @RequestParam String password,
                                           @RequestParam Long actorId) {
        User user = userService.findUser(email, password);
        if (user != null) {
            Actor actor = actorService.addFavoriteActor(user, actorId);
            return ResponseEntity.status(HttpStatus.OK).body(actor);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad credentials");
        }
    }

    @RequestMapping(value = "/", method = DELETE)
    public ResponseEntity deletePerson(@RequestParam String email,
                                       @RequestParam String password,
                                       @RequestParam Long tmdbActorId) {
        User user = userService.findUser(email, password);
        if (user != null) {
            actorService.deleteFavoriteActor(user, tmdbActorId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad credentials");
        }
    }
}
