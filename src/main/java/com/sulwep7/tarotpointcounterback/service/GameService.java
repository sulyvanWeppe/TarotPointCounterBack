package com.sulwep7.tarotpointcounterback.service;

import com.sulwep7.tarotpointcounterback.mapper.GameMapper;
import com.sulwep7.tarotpointcounterback.model.entity.Game;
import com.sulwep7.tarotpointcounterback.model.entity.GameWDetails;
import com.sulwep7.tarotpointcounterback.model.exception.DataStoringException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Setter
@Slf4j
public class GameService {
    @Autowired
    GameMapper gameMapper;

    public List<Game> getGames() {
        log.info("Get all games");
        return gameMapper.getAllGames();
    }

    public Map<String,List<GameWDetails>> getAllGamesWDetails() {
        log.info("Get all games with their details");
        List<GameWDetails> gameWDetailsList = gameMapper.getAllGamesWDetails();

        Map<String,List<GameWDetails>> gamesWDetailsByUuid = gameWDetailsList.stream().collect(Collectors.groupingBy(GameWDetails::getGameUuid));

        return gamesWDetailsByUuid;
    }

    public UUID insertNewGame(int nrPlayers) throws DataStoringException {
        try {
            UUID uuid = UUID.randomUUID();
            log.info("Insert new game of {} players",nrPlayers);
            gameMapper.insertGame(uuid.toString(),Timestamp.from(Instant.now()),nrPlayers);

            return uuid;
        } catch(Exception e) {
            log.error("Error while inserting new Game : [{}]",e);
            throw new DataStoringException("Error inserting the new game in the DB");
        }
    }
}
