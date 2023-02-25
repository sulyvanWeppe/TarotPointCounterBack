package com.sulwep7.tarotpointcounterback.controller;

import com.sulwep7.tarotpointcounterback.model.PlayerScore;
import com.sulwep7.tarotpointcounterback.model.PlayersScorePatchRequest;
import com.sulwep7.tarotpointcounterback.model.PlayersScorePostRequest;
import com.sulwep7.tarotpointcounterback.service.PlayersScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class PlayerScoreController {

    @Autowired
    private PlayersScoreService playersScoreService;

    @PostMapping("/score")
    public ResponseEntity<String> insertPlayerScore(@RequestBody PlayerScore newPlayerScore) {
        try {
            playersScoreService.insertPlayerScore(newPlayerScore.getGameUuid(),newPlayerScore.getPlayerName(),newPlayerScore.getPlayerScore());
        } catch(Exception e) {
            return new ResponseEntity<>("Error inserting the new player score in the DB", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/scores")
    public ResponseEntity<String> insertPlayersScore(@RequestBody PlayersScorePostRequest playersScorePostRequest) {
        try {
            playersScoreService.insertPlayersScore(playersScorePostRequest.getGameUuid(),playersScorePostRequest.getPlayerScorePostRequestList());
        } catch(Exception e) {
            return new ResponseEntity<>("Error inserting the new players' scores in the DB", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/scores")
    public ResponseEntity<String> updatePlayersScore(@RequestBody PlayersScorePatchRequest playersScorePatchRequest) {
        String gameUuid = playersScorePatchRequest.getGameUuid();
        try {
            playersScoreService.updatePlayersScore(gameUuid,playersScorePatchRequest.getPlayerScorePatchRequests());
        } catch(Exception e) {
            return new ResponseEntity<>("Error when trying to update the scores",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
