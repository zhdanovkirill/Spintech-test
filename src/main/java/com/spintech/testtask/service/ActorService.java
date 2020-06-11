package com.spintech.testtask.service;

import com.spintech.testtask.entity.Actor;
import com.spintech.testtask.entity.User;

import java.util.List;

public interface ActorService {

    Actor addFavoriteActor(User user, Long tmdbActorId);

    List<Actor> getAllFavoriteActor(User user);

    void deleteFavoriteActor(User user, Long tmdbActorId);
}

