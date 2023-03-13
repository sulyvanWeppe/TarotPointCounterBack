package com.sulwep7.tarotpointcounterback.unit.service;

import com.sulwep7.tarotpointcounterback.mapper.PlayersScoreMapper;
import com.sulwep7.tarotpointcounterback.model.exception.DataStoringException;
import com.sulwep7.tarotpointcounterback.service.PlayersScoreService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import java.util.UUID;

@SpringBootTest
@Slf4j
class PlayersScoreServiceUnitTest {

    private static PlayersScoreService playersScoreService;
    private static PlayersScoreMapper playersScoreMapper;

    @BeforeAll
    static void initMock() {
        log.info("Initialize Mock(s)");
        playersScoreService = new PlayersScoreService();
        playersScoreMapper = Mockito.mock(PlayersScoreMapper.class);
        playersScoreService.setPlayersScoreMapper(playersScoreMapper);
    }

    @Test
    void insertPlayerScore() throws DataStoringException {
        //GIVEN

        //WHEN
        playersScoreService.insertPlayerScore(UUID.randomUUID().toString(),"player",10);

        //THEN
        Mockito.verify(playersScoreMapper).insertPlayerScore(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(),ArgumentMatchers.anyInt());
    }

    @Test
    void updatePlayerScore() throws DataStoringException {
        //GIVEN

        //WHEN
        playersScoreService.updatePlayerScore(UUID.randomUUID().toString(),"player",10);

        //THEN
        Mockito.verify(playersScoreMapper).updatePlayerScore(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(),ArgumentMatchers.anyInt());
    }
}
