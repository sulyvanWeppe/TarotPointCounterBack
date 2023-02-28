package com.sulwep7.tarotpointcounterback.controller;

import com.sulwep7.tarotpointcounterback.model.entity.Game;
import com.sulwep7.tarotpointcounterback.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/game")
@Slf4j
public class GameController {

    @Autowired
    GameService gameService;

    @GetMapping
    public ResponseEntity<List<Game>> getGames() {
        List<Game> games = gameService.getGames();
        if(games==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> insertGame(@RequestBody Game newGame) {
         try {
             UUID uuid = gameService.insertNewGame(newGame.getNrPlayers());

             return new ResponseEntity<>(uuid.toString(),HttpStatus.CREATED);
         } catch(Exception e) {
             return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }
}
