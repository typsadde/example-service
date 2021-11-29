package com.sadde.exampleservice.match.service;

import com.sadde.exampleservice.common.util.exceptions.NotFoundException;
import com.sadde.exampleservice.entry.domain.Entry;
import com.sadde.exampleservice.match.domain.Match;
import com.sadde.exampleservice.match.repository.MatchRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Service
@Slf4j
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class MatchService {
  private final MatchRepository matchRepository;

  public Match findOrCreateMatch(String playerId, String gameId) {
    var match =
        matchRepository
            .findOneByStartedAtNullOrderByCreatedAt(gameId)
            .orElseGet(() -> createMatch(playerId));
    var matchWithPlayer = match.addPlayerToMatch(createEntry(playerId));
    return matchRepository.save(matchWithPlayer);
  }

  public Match createMatch(String gameId) {
    return Match.createNew(UUID.randomUUID().toString(), gameId);
  }

  public Match getMatch(String matchId) {
    return matchRepository
        .findById(matchId)
        .orElseThrow(
            () -> new NotFoundException(String.format("Match with id %s not found", matchId)));
  }

  public Match startMatch(String matchId) {
    return matchRepository
        .findById(matchId)
        .map(match -> match.start(Instant.now()))
        .orElseThrow(
            () -> new NotFoundException(String.format("Match with id %s not found", matchId)));
  }

  private Entry createEntry(String playerId) {
    return Entry.from(UUID.randomUUID().toString(), playerId);
  }
}
