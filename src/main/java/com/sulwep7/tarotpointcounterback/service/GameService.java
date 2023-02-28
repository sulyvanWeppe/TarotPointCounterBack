package com.sulwep7.tarotpointcounterback.service;

import com.sulwep7.tarotpointcounterback.mapper.GameMapper;
import com.sulwep7.tarotpointcounterback.model.entity.Game;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class GameService {
    @Autowired
    GameMapper gameMapper;

    public List<Game> getGames() {
        return gameMapper.getAllGames();
    }

    public UUID insertNewGame(int nrPlayers) throws Exception {
        try {
            UUID uuid = UUID.randomUUID();
            log.info("Insert new game of {} players",nrPlayers);
            gameMapper.insertGame(uuid.toString(),Timestamp.from(Instant.now()),nrPlayers);

            return uuid;
        } catch(Exception e) {
            log.error("Error while inserting new Game : [{}]",e);
            throw new Exception("Error inserting the new game in the DB");
        }
    }
}
