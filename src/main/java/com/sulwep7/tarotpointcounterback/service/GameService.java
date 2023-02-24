package com.sulwep7.tarotpointcounterback.service;

import com.sulwep7.tarotpointcounterback.mapper.GameMapper;
import com.sulwep7.tarotpointcounterback.model.Game;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class GameService {
    @Autowired
    GameMapper gameMapper;

    public List<Game> getGames() {
        return gameMapper.getAllGames();
    }

    public void insertNewGame(int nrPlayers) throws Exception {
        try {
            log.info("Insert new game of {} players",nrPlayers);
            gameMapper.insertGame(Timestamp.from(Instant.now()),nrPlayers);
        } catch(Exception e) {
            log.error("Error while inserting new Game : [{}]",e);
            throw new Exception("Error inserting the new game in the DB");
        }
    }
}
