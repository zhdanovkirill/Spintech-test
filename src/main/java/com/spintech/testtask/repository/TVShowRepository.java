package com.spintech.testtask.repository;

import com.spintech.testtask.entity.TVShow;
import com.spintech.testtask.infrastructure.enums.TVShowStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TVShowRepository extends CrudRepository<TVShow, Long> {

    @Query("select ts from  TVShow ts " +
            "where ts.user.id = :userId")
    List<TVShow> findAllTVShowByUser(Long userId);

    @Query("select ts from  TVShow ts " +
            "where ts.user.id = :userId and ts.tmdbId =:tvShowId")
    Optional<TVShow> findByIdAndUser(Long tvShowId, Long userId);

    @Query("select ts from  TVShow ts " +
            "where ts.user.id = :userId and ts.status =:status")
    List<TVShow> findAllByStatus(Long userId, TVShowStatus status);
}