package com.spintech.testtask.repository;

import com.spintech.testtask.entity.Actor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ActorRepository extends CrudRepository<Actor, Long> {

    @Query("select a from  Actor a " +
            "where a.tmdbId = :tmdbActorId and a.user.id = :userId")
    Optional<Actor> findByIdAndTMId(Long tmdbActorId, Long userId);

   @Query("select a from Actor a where a.user.id = :userId")
    List<Actor> findByUserId(Long userId);
}