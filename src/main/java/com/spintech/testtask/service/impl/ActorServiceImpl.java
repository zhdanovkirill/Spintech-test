package com.spintech.testtask.service.impl;

import com.spintech.testtask.entity.Actor;
import com.spintech.testtask.entity.User;
import com.spintech.testtask.exceptions.EntityAlreadyExistException;
import com.spintech.testtask.exceptions.EntityNotFoundException;
import com.spintech.testtask.repository.ActorRepository;
import com.spintech.testtask.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    private ActorRepository actorRepository;


    @Override
    public Actor addFavoriteActor(User user, Long tmdbActorId) {
        if (actorRepository.findByIdAndTMId(tmdbActorId, user.getId()).isPresent()) {
            throw new EntityAlreadyExistException("Actor", tmdbActorId.toString());
        }
        Actor actor = Actor.builder().tmdbId(tmdbActorId).user(user).build();
        return actorRepository.save(actor);
    }

    @Override
    public List<Actor> getAllFavoriteActor(User user) {
        return actorRepository.findByUserId(user.getId());
    }

    @Override
    public void deleteFavoriteActor(User user, Long tmdbActorId) {
        Actor actor = actorRepository.findByIdAndTMId(tmdbActorId, user.getId()).orElseThrow(() -> new EntityNotFoundException("Actor", tmdbActorId.toString()));
        actorRepository.delete(actor);
    }

}