package com.sulwep7.tarotpointcounterback.integration.service;

import com.sulwep7.tarotpointcounterback.model.dto.PlayerScorePatchRequest;
import com.sulwep7.tarotpointcounterback.model.dto.PlayerScorePostRequest;
import com.sulwep7.tarotpointcounterback.model.entity.GameWDetails;
import com.sulwep7.tarotpointcounterback.model.exception.DataStoringException;
import com.sulwep7.tarotpointcounterback.service.GameService;
import com.sulwep7.tarotpointcounterback.service.PlayersScoreService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
@Slf4j
class PlayersScoreServiceIntegrationTest {

    @Autowired
    private PlayersScoreService playersScoreService;
    @Autowired
    private GameService gameService;

    @BeforeAll
    static void populateDB(@Autowired DataSource dataSource) {
        try {
            Connection connection = dataSource.getConnection();
            ScriptUtils.executeSqlScript(connection,new ClassPathResource("/script/mySql/data.sql"));
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String createGame(int nrPlayers) throws DataStoringException{
        UUID gameUuid = gameService.insertNewGame(3);
        log.info("Game created with Uuid {}",gameUuid.toString());
        Assertions.assertNotNull(gameUuid);

        return gameUuid.toString();
    }

    private String createGameWPlayersOneByONe(int nrPlayers) throws DataStoringException {
        log.info("Step 1: create game of {} players",nrPlayers);
        String gameUuid = createGame(nrPlayers);

        log.info("Step 2: create {} player scores for game {} at the same time",nrPlayers,gameUuid);
        for(int i=0; i<nrPlayers; i++) {
            playersScoreService.insertPlayerScore(gameUuid,"player"+i,0);

        }
        Map<String,List<GameWDetails>> allGames = gameService.getAllGamesWDetails();
        List<GameWDetails> gameWDetailsList = allGames.get(gameUuid);
        log.info("Retrieve {} player's detail for game {}",gameWDetailsList.size(),gameUuid);
        Assertions.assertTrue(gameWDetailsList!=null && gameWDetailsList.size()==nrPlayers);

        for(int i=0; i<nrPlayers; i++) {
            GameWDetails gameWDetailsPlayer = gameWDetailsList.get(i);
            Assertions.assertTrue(gameWDetailsPlayer.getPlayerName().equals("player"+i) && gameWDetailsPlayer.getPlayerScore()==0);
        }

        return gameUuid;
    }

    @Test
    void insertPlayerScore() throws DataStoringException{
        log.info("Test inserting player score (insert players one by one)");
        createGameWPlayersOneByONe(3);
    }

    private String createGameWPlayersGrouped(int nrPlayers) throws DataStoringException {
        log.info("Step 1: create game of {} players",nrPlayers);
        String gameUuid = createGame(nrPlayers);
        Assertions.assertNotNull(gameUuid);

        log.info("Step 2: create {} player scores for game {} at the same time",nrPlayers,gameUuid);
        List<PlayerScorePostRequest> playerScorePostRequestList = new ArrayList<>();
        for(int i=0; i<nrPlayers; i++) {
            PlayerScorePostRequest playerScorePostRequest = PlayerScorePostRequest.builder()
                    .playerName("Player"+i)
                    .playerScore(0)
                    .build();
            playerScorePostRequestList.add(playerScorePostRequest);
        }
        playersScoreService.insertPlayersScore(gameUuid,playerScorePostRequestList);
        Map<String,List<GameWDetails>> allGames = gameService.getAllGamesWDetails();
        List<GameWDetails> gameWDetailsList = allGames.get(gameUuid);
        log.info("Retrieve {} player's details for game {}",gameWDetailsList.size(),gameUuid);
        Assertions.assertTrue(gameWDetailsList!=null && gameWDetailsList.size()==nrPlayers);

        for(int i=0; i<nrPlayers; i++) {
            GameWDetails gameWDetailsPlayer = gameWDetailsList.get(i);
            Assertions.assertTrue(gameWDetailsPlayer.getPlayerName().equals("Player"+i) && gameWDetailsPlayer.getPlayerScore()==0);
        }

        return gameUuid;
    }

    @Test
    void insertPlayersScore() throws DataStoringException{
        log.info("Test inserting multiple player scores for 1 game");

        createGameWPlayersGrouped(3);
    }

    @Test
    void updatePlayersScore() throws DataStoringException {
        log.info("Test updating score of all players for a given game");

        String gameUuid = createGameWPlayersGrouped(3);

        log.info("Step 3: update players");
        List<PlayerScorePatchRequest> playerScorePatchRequestList = new ArrayList<>();
        for(int i=0; i<3; i++) {
            PlayerScorePatchRequest playerScorePatchRequest = PlayerScorePatchRequest.builder()
                    .playerName("Player"+i)
                    .playerScore(10)
                    .build();
            playerScorePatchRequestList.add(playerScorePatchRequest);
        }

        playersScoreService.updatePlayersScore(gameUuid,playerScorePatchRequestList);

        //Check scores correctly updated
        Map<String,List<GameWDetails>> allGames = gameService.getAllGamesWDetails();
        List<GameWDetails> gamePlayers = allGames.get(gameUuid);
        for(int i=0; i<3; i++) {
            GameWDetails player = gamePlayers.get(i);
            Assertions.assertEquals(10,player.getPlayerScore());
        }
    }
}
