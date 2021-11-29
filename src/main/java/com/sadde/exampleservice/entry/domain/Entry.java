package com.sadde.exampleservice.entry.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@Value
public class Entry {
  @Id
  @Column(name = "id")
  String id;

  @Column(name = "player_id")
  String playerId;

  @Builder.Default
  @Column(name = "scores")
  List<Score> scores = new ArrayList<>();

  public static Entry from(String id, String playerId) {
    return Entry.builder().id(id).playerId(playerId).build();
  }
}
