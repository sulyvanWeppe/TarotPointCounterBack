package com.sulwep7.tarotpointcounterback.service;

import com.sulwep7.tarotpointcounterback.model.entity.GameWDetails;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
@Slf4j
public class PlayersScoreServiceTest {

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

    private String createGame(int nrPlayers) throws Exception{
        UUID gameUuid = gameService.insertNewGame(3);
        log.info("Game created with Uuid {}",gameUuid.toString());
        Assertions.assertNotNull(gameUuid);

        return gameUuid.toString();
    }

    @Test
    public void insertPlayerScore() throws Exception{
        log.info("Test inserting player score");

        log.info("Step 1: create game of 3 players");
        String gameUuid = createGame(3);
        log.info("Game created with Uuid {}",gameUuid);
        Assertions.assertNotNull(gameUuid);

        log.info("Step 2: create 3 player score for game {}",gameUuid);
        playersScoreService.insertPlayerScore(gameUuid,"player1",0);
        playersScoreService.insertPlayerScore(gameUuid,"player2",0);
        playersScoreService.insertPlayerScore(gameUuid,"player3",0);

        Map<String,List<GameWDetails>> allGames = gameService.getAllGamesWDetails();
        List<GameWDetails> gameWDetailsList = allGames.get(gameUuid);
        log.info("Retrieve {} player's detail for game {}",gameWDetailsList.size(),gameUuid);
        Assertions.assertTrue(gameWDetailsList!=null && gameWDetailsList.size()==3);

        GameWDetails gameWDetailsPlayer1 = gameWDetailsList.get(0);
        GameWDetails gameWDetailsPlayer2 = gameWDetailsList.get(1);
        GameWDetails gameWDetailsPlayer3 = gameWDetailsList.get(2);
        Assertions.assertTrue(gameWDetailsPlayer1.getPlayerName().equals("player1") && gameWDetailsPlayer1.getPlayerScore()==0);
        Assertions.assertTrue(gameWDetailsPlayer2.getPlayerName().equals("player2") && gameWDetailsPlayer2.getPlayerScore()==0);
        Assertions.assertTrue(gameWDetailsPlayer3.getPlayerName().equals("player3") && gameWDetailsPlayer3.getPlayerScore()==0);
    }

    @Test
    private void insertPlayersScore() throws Exception{
        log.info("Test inserting multiple player scores for 1 game");

        log.info("Step 1: create game of 3 players");
        String gameUuid = createGame(3);//HEREEEEEEEE
    }
}
