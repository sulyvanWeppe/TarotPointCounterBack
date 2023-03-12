package com.sulwep7.tarotpointcounterback.unit.service;

import com.sulwep7.tarotpointcounterback.mapper.GameMapper;
import com.sulwep7.tarotpointcounterback.model.entity.Game;
import com.sulwep7.tarotpointcounterback.model.entity.GameWDetails;
import com.sulwep7.tarotpointcounterback.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.BDDMockito.*;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@SpringBootTest
@Slf4j
@EnabledIf(expression = "#{environment['spring.profiles.active'] == 'unit-test'}", loadContext = true)
public class GameServiceUnitTest {

    private static GameService gameService;
    private static GameMapper gameMapper;

    @BeforeAll
    public static void initMock() {
        log.info("Initialize Mock(s)");
        gameMapper = Mockito.mock(GameMapper.class);
        gameService = new GameService();
        gameService.setGameMapper(gameMapper);
    }

    @Test
    public void getGames() {
        //GIVEN
        Game game1 = new Game(UUID.randomUUID().toString(), Timestamp.from(Instant.now()),4);
        Game game2 = new Game(UUID.randomUUID().toString(), Timestamp.from(Instant.now()),4);
        Mockito.when(gameMapper.getAllGames()).thenReturn(List.of(game1,game2));

        //WHEN
        List<Game> gameList = gameService.getGames();

        //THEN
        Mockito.verify(gameMapper).getAllGames();
        Assert.assertTrue(gameList.size()==2 && gameList.get(0).equals(game1) && gameList.get(1).equals(game2));
    }

    @Test
    public void getAllGamesWDetails() {
        //GIVEN
        String gameUuid = UUID.randomUUID().toString();
        Timestamp timestamp = Timestamp.from(Instant.now());
        GameWDetails player1 = new GameWDetails(gameUuid,timestamp,4,"player1",10);
        GameWDetails player2 = new GameWDetails(gameUuid,timestamp,4,"player2",10);
        GameWDetails player3 = new GameWDetails(gameUuid,timestamp,4,"player3",10);
        GameWDetails player4 = new GameWDetails(gameUuid,timestamp,4,"player4",10);
        Mockito.when(gameMapper.getAllGamesWDetails()).thenReturn(List.of(player1,player2,player3,player4));

        //WHEN
        Map<String,List<GameWDetails>> allGames = gameService.getAllGamesWDetails();

        //THEN
        Mockito.verify(gameMapper).getAllGamesWDetails();
        Assert.assertTrue(allGames.keySet().size()==1 && allGames.containsKey(gameUuid) && allGames.get(gameUuid).size()==4);
    }

    @Test
    public void insertNewGame() throws Exception {
        //GIVEN

        //WHEN
        gameService.insertNewGame(4);

        //THEN
        Mockito.verify(gameMapper).insertGame(ArgumentMatchers.anyString(),ArgumentMatchers.any(Timestamp.class),ArgumentMatchers.anyInt());
    }
}
