package com.sulwep7.tarotpointcounterback.controller;

import com.sulwep7.tarotpointcounterback.annotation.metrics.EnableApiMetric;
import com.sulwep7.tarotpointcounterback.model.dto.GamePostRequest;
import com.sulwep7.tarotpointcounterback.model.dto.GameWDetailsPlayerResponse;
import com.sulwep7.tarotpointcounterback.model.dto.GameWDetailsResponse;
import com.sulwep7.tarotpointcounterback.model.dto.GamesWDetailsResponse;
import com.sulwep7.tarotpointcounterback.model.entity.Game;
import com.sulwep7.tarotpointcounterback.model.entity.GameWDetails;
import com.sulwep7.tarotpointcounterback.service.GameService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
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
    @ApiOperation(value = "Getting all the games")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The list of the games was successfully retrieved"),
            @ApiResponse(code = 404, message = "No game was found")
    })
    @EnableApiMetric
    public ResponseEntity<List<Game>> getGames(HttpServletRequest httpServletRequest) {
        List<Game> games = gameService.getGames();
        if(games==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/gamesWDetails")
    @ApiOperation(value = "Getting all the games with their details. Their details include the number of players and for each one his name and score")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The list of the games with their details was retrieved"),
            @ApiResponse(code = 404, message = "No game was found")
    })
    @EnableApiMetric
    public ResponseEntity<GamesWDetailsResponse> getAllGamesWDetails(HttpServletRequest httpServletRequest) {
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

        response.getGameWDetailsResponseList().sort(Comparator.comparing(GameWDetailsResponse::getTimestamp).reversed());

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/game")
    @ApiOperation(value = "Inserting new game")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New game is inserted",response = String.class, examples = @Example(
                    value = @ExampleProperty(value = "toto")
            )),
            @ApiResponse(code = 500, message = "Internal server error occurred")
    })
    @EnableApiMetric
    public ResponseEntity<String> insertGame(@ApiParam(value="New game to be inserted") @RequestBody GamePostRequest newGame, HttpServletRequest httpServletRequest) {
         try {
             UUID uuid = gameService.insertNewGame(newGame.getNrPlayers());

             return new ResponseEntity<>(uuid.toString(),HttpStatus.CREATED);
         } catch(Exception e) {
             return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }
}
