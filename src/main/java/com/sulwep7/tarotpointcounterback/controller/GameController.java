package com.sulwep7.tarotpointcounterback.controller;

import com.sulwep7.tarotpointcounterback.model.dto.GameWDetailsPlayerResponse;
import com.sulwep7.tarotpointcounterback.model.dto.GameWDetailsResponse;
import com.sulwep7.tarotpointcounterback.model.dto.GamesWDetailsResponse;
import com.sulwep7.tarotpointcounterback.model.entity.Game;
import com.sulwep7.tarotpointcounterback.model.entity.GameWDetails;
import com.sulwep7.tarotpointcounterback.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@Slf4j
public class GameController {

    @Autowired
    GameService gameService;

    @GetMapping("/games")
    public ResponseEntity<List<Game>> getGames() {
        List<Game> games = gameService.getGames();
        if(games==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/gamesWDetails")
    public ResponseEntity<GamesWDetailsResponse> getAllGamesWDetails() {
        Map<String, List<GameWDetails>> gamesWDetailsByUuid = gameService.getAllGamesWDetails();
        if(gamesWDetailsByUuid==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        GamesWDetailsResponse response = new GamesWDetailsResponse();
        gamesWDetailsByUuid.entrySet().stream().forEach(entry ->
                response.getGameWDetailsResponseList().add(
                    GameWDetailsResponse.builder()
                            .gameUuid(entry.getKey())
                            .timestamp(entry.getValue().get(0).getTimestamp()) //can take the first one since the timestamp is the same for all
                            .nrPlayers(entry.getValue().size())
                            .gameWDetailsPlayerResponseList(
                                    entry.getValue().stream().map(playerDetails ->  GameWDetailsPlayerResponse.builder()
                                                .playerName(playerDetails.getPlayerName())
                                                .playerScore(playerDetails.getPlayerScore())
                                                .build())
                                            .collect(Collectors.toList()))
                            .build()
                )
        );

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/game")
    public ResponseEntity<String> insertGame(@RequestBody Game newGame) {
         try {
             UUID uuid = gameService.insertNewGame(newGame.getNrPlayers());

             return new ResponseEntity<>(uuid.toString(),HttpStatus.CREATED);
         } catch(Exception e) {
             return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }
}
