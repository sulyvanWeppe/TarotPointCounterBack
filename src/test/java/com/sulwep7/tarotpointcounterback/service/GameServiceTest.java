package com.sulwep7.tarotpointcounterback.service;

import com.sulwep7.tarotpointcounterback.model.entity.Game;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;

//@RunWith(SpringRunner.class)
//@MybatisTest(properties = {"mybatis.mapperLocations=classpath*:script/mySql/*.xml"})
@SpringBootTest
@Slf4j
public class GameServiceTest {

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
        log.info("Test getting all the games from the DB");

        List<Game> games = gameService.getGames();
        log.info("Retrieve {} games",games.size());

        Assertions.assertTrue(!ObjectUtils.isEmpty(games));
    }

}
