package com.sulwep7.tarotpointcounterback.service;

import com.sulwep7.tarotpointcounterback.model.entity.Game;
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
import java.util.UUID;

@SpringBootTest
@Slf4j
public class GameServiceTest {

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

    @Autowired
    private GameService gameService;

    @Test
    public void insertAndCheck() {
        log.info("Test inserting new game with 4 players");
        try {
            UUID uuid = gameService.insertNewGame(4);
            log.info("Game created with UUID : {}",uuid.toString());

            Assertions.assertNotNull(uuid.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getGames() {
        log.info("Test getting all the games from DB");

        List<Game> games = gameService.getGames();
        log.info("Retrieve {} games",games.size());

        Assertions.assertTrue(!ObjectUtils.isEmpty(games));
    }

    @Test
    public void getAllGamesWDetails() {
        log.info("Test getting all the games with details from DB");

        List<GameWDetails> gameWDetailsList = gameService.gameMapper.getAllGamesWDetails();
        log.info("Retrieve {} games with their details", gameWDetailsList.size());

        Assertions.assertTrue(!ObjectUtils.isEmpty(gameWDetailsList));

    }

}