package com.sadde.exampleservice.match.domain;

import com.sadde.exampleservice.entry.domain.Entry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@Value
public class Match {
  @Id
  @Column(name = "id")
  String id;

  @Column(name = "state")
  MatchState state;

  @Column(name = "game_id")
  String gameId;

  @Builder.Default
  @Column(name = "players")
  Set<Entry> players = new HashSet<>();

  @Column(name = "number_of_players")
  int numberOfPlayers;

  @Column(name = "started_at")
  Instant startedAt;

  @Column(name = "completed_at")
  Instant completedAt;

  public static Match createNew(String id, String gameId) {
    return Match.builder().id(id).state(MatchState.AWAITING_PLAYERS).gameId(gameId).build();
  }

  public Match start(Instant startedAt) {
    return this.toBuilder().state(MatchState.RUNNING).startedAt(startedAt).build();
  }

  public Match complete(Instant completedAt) {
    return this.toBuilder().state(MatchState.COMPLETED).completedAt(completedAt).build();
  }

  public Match cancel(Instant cancelledAt) {
    return this.toBuilder().state(MatchState.CANCELLED).completedAt(cancelledAt).build();
  }

  public Match addPlayerToMatch(Entry entry) {
    if (players.size() >= numberOfPlayers) {
      throw new IllegalStateException("Match is full, cannot add more players");
    }
    var newPlayers = Stream.concat(players.stream(), Stream.of(entry)).collect(Collectors.toSet());
    return toBuilder().players(newPlayers).build();
  }

  public enum MatchState {
    AWAITING_PLAYERS,
    RUNNING,
    COMPLETED,
    CANCELLED
  }
}
