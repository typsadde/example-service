package com.sadde.exampleservice.match.repository;

import com.sadde.exampleservice.match.domain.Match;

import java.util.Optional;

public interface MatchRepository {

  /*
   * SELECT * FROM matches WHERE started_at is NULL AND game_id = :gameId
   * ORDER BY created_at LIMIT 1
   * */
  Optional<Match> findOneByStartedAtNullOrderByCreatedAt(String gameId);

  Optional<Match> findById(String id);

  Match save(Match match);
}
