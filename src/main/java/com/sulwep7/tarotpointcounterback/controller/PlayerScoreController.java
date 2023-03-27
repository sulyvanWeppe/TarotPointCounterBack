package com.sulwep7.tarotpointcounterback.controller;

import com.sulwep7.tarotpointcounterback.annotation.metrics.EnableApiMetric;
import com.sulwep7.tarotpointcounterback.model.entity.PlayerScore;
import com.sulwep7.tarotpointcounterback.model.dto.PlayersScorePatchRequest;
import com.sulwep7.tarotpointcounterback.model.dto.PlayersScorePostRequest;
import com.sulwep7.tarotpointcounterback.service.PlayersScoreService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping
public class PlayerScoreController {

    @Autowired
    private PlayersScoreService playersScoreService;

    @PostMapping("/score")
    @ApiOperation(value="Inserting new player's score in the DB")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New player's score was added"),
            @ApiResponse(code = 500, message = "Internal server error occurred")
    })
    @EnableApiMetric
    public ResponseEntity<String> insertPlayerScore(@ApiParam(value = "New player's score to be added") @RequestBody PlayerScore newPlayerScore, HttpServletRequest httpServletRequest) {
        try {
            playersScoreService.insertPlayerScore(newPlayerScore.getGameUuid(),newPlayerScore.getName(),newPlayerScore.getScore());
        } catch(Exception e) {
            return new ResponseEntity<>("Error inserting the new player score in the DB", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/scores")
    @ApiOperation(value="Inserting multiple new player's scores in the DB")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New player's scores were added"),
            @ApiResponse(code = 500, message = "Internal server error occurred")
    })
    @EnableApiMetric
    public ResponseEntity<String> insertPlayersScore(@ApiParam(value = "New player's scores to be added") @RequestBody PlayersScorePostRequest playersScorePostRequest, HttpServletRequest httpServletRequest) {
        try {
            playersScoreService.insertPlayersScore(playersScorePostRequest.getGameUuid(),playersScorePostRequest.getPlayerScorePostRequestList());
        } catch(Exception e) {
            return new ResponseEntity<>("Error inserting the new players' scores in the DB", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/scores")
    @ApiOperation(value="Updating scores of multiple players")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Scores were updated"),
            @ApiResponse(code = 500, message = "Internal server error occurred")
    })
    @EnableApiMetric
    public ResponseEntity<String> updatePlayersScore(@ApiParam(value="Scores to be updated") @RequestBody PlayersScorePatchRequest playersScorePatchRequest, HttpServletRequest httpServletRequest) {
        String gameUuid = playersScorePatchRequest.getGameUuid();
        try {
            playersScoreService.updatePlayersScore(gameUuid,playersScorePatchRequest.getPlayerScorePatchRequests());
        } catch(Exception e) {
            return new ResponseEntity<>("Error when trying to update the scores",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
