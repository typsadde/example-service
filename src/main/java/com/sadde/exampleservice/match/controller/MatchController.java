package com.sadde.exampleservice.match.controller;

import com.sadde.exampleservice.common.util.exceptions.NoAuthException;
import com.sadde.exampleservice.match.domain.Match;
import com.sadde.exampleservice.match.service.MatchService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

import static com.sadde.exampleservice.ExampleServiceApplication.API_VERSION_1;

@RestController
@AllArgsConstructor
@RequestMapping(API_VERSION_1 + "/matches")
public class MatchController {
  private final MatchService matchService;

  @ApiOperation(value = "Get authenticated player's claim state", notes = "Returns claim state")
  @GetMapping("/{id}")
  public Match getMatch(@PathVariable("id") String id) {
    return matchService.getMatch(id);
  }

  @ApiOperation(value = "Enroll to game")
  @PostMapping("/{gameId}/enroll")
  public Match enrollToGame(@PathVariable("gameId") String gameId, Authentication authentication) {
    var playerId =
        Optional.ofNullable(authentication)
            .map(Principal::getName)
            .orElseThrow(NoAuthException::new);
    return matchService.findOrCreateMatch(playerId, gameId);
  }
}
