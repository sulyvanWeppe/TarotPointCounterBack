package com.sulwep7.tarotpointcounterback.unit.service;

import com.sulwep7.tarotpointcounterback.mapper.PlayersScoreMapper;
import com.sulwep7.tarotpointcounterback.model.dto.PlayerScorePatchRequest;
import com.sulwep7.tarotpointcounterback.model.dto.PlayerScorePostRequest;
import com.sulwep7.tarotpointcounterback.model.entity.PlayerScore;
import com.sulwep7.tarotpointcounterback.model.exception.DataStoringException;
import com.sulwep7.tarotpointcounterback.service.PlayersScoreService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Slf4j
class PlayersScoreServiceUnitTest {

    @Autowired
    private PlayersScoreService playersScoreService;
    @MockBean
    PlayersScoreMapper playersScoreMapper;

    @Test
    void insertPlayerScoreOk() throws Exception {
        //GIVEN

        //WHEN
        playersScoreService.insertPlayerScore(UUID.randomUUID().toString(),"player",10);

        //THEN
        Mockito.verify(playersScoreMapper).insertPlayerScore(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(),ArgumentMatchers.anyInt());
    }

    @Test
    void insertPlayerScoreException() throws Exception {
        //GIVEN
        String gameUuid = UUID.randomUUID().toString();
        String playerName = "player";
        int playerScore = 10;
        Mockito.doThrow(new Exception("Error")).when(playersScoreMapper).insertPlayerScore(gameUuid,playerName,playerScore);

        //WHEN
        //THEN
        Assert.assertThrows(DataStoringException.class,() -> playersScoreService.insertPlayerScore(gameUuid,playerName,playerScore));
    }

    @Test
    void insertPlayersScoreExceptionCatched() throws Exception {
        //GIVEN
        String gameUuid = UUID.randomUUID().toString();
        String player1Name = "player1";
        String player2Name = "player2";
        String player3Name = "player3";
        int player1Score = 10;
        int player2Score = 10;
        int player3Score = 10;
        PlayerScorePostRequest player1 = PlayerScorePostRequest.builder().playerName(player1Name).playerScore(player1Score).build();
        PlayerScorePostRequest player2 = PlayerScorePostRequest.builder().playerName(player2Name).playerScore(player2Score).build();
        PlayerScorePostRequest player3 = PlayerScorePostRequest.builder().playerName(player3Name).playerScore(player3Score).build();
        Mockito.doThrow(new Exception()).when(playersScoreMapper).insertPlayerScore(gameUuid,player2Name,player2Score);
        Mockito.doNothing().when(playersScoreMapper).deletePlayerScore(ArgumentMatchers.anyString(),ArgumentMatchers.anyString());

        //WHEN
        //THEN
        DataStoringException exception = Assert.assertThrows(DataStoringException.class,() -> playersScoreService.insertPlayersScore(gameUuid, List.of(player1,player2,player3)));
        Mockito.verify(playersScoreMapper,Mockito.times(2)).insertPlayerScore(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(),ArgumentMatchers.anyInt());
        Mockito.verify(playersScoreMapper).deletePlayerScore(ArgumentMatchers.anyString(),ArgumentMatchers.anyString());
        Assertions.assertEquals("Error updating scores of players for gameId "+gameUuid,exception.getMessage());
    }

    @Test
    void insertPlayersScoreException() throws Exception {
        //GIVEN
        String gameUuid = UUID.randomUUID().toString();
        String player1Name = "player1";
        String player2Name = "player2";
        String player3Name = "player3";
        int player1Score = 10;
        int player2Score = 10;
        int player3Score = 10;
        PlayerScorePostRequest player1 = PlayerScorePostRequest.builder().playerName(player1Name).playerScore(player1Score).build();
        PlayerScorePostRequest player2 = PlayerScorePostRequest.builder().playerName(player2Name).playerScore(player2Score).build();
        PlayerScorePostRequest player3 = PlayerScorePostRequest.builder().playerName(player3Name).playerScore(player3Score).build();
        Mockito.doThrow(new Exception()).when(playersScoreMapper).insertPlayerScore(gameUuid,player2Name,player2Score);
        Mockito.doThrow(new Exception()).when(playersScoreMapper).deletePlayerScore(ArgumentMatchers.anyString(),ArgumentMatchers.anyString());

        //WHEN
        //THEN
        DataStoringException exception = Assert.assertThrows(DataStoringException.class,() -> playersScoreService.insertPlayersScore(gameUuid,List.of(player1,player2,player3)));
        Mockito.verify(playersScoreMapper,Mockito.times(2)).insertPlayerScore(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(),ArgumentMatchers.anyInt());
        Mockito.verify(playersScoreMapper).deletePlayerScore(ArgumentMatchers.anyString(),ArgumentMatchers.anyString());
        Assertions.assertEquals("Error when trying to rollback score creation for player player1",exception.getMessage());
    }

    @Test
    void updatePlayerScoreOk() throws Exception {
        //GIVEN

        //WHEN
        playersScoreService.updatePlayerScore(UUID.randomUUID().toString(),"player",10);

        //THEN
        Mockito.verify(playersScoreMapper).updatePlayerScore(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(),ArgumentMatchers.anyInt());
    }

    @Test
    void updatePlayerScoreExceptionCatched() throws Exception {
        //GIVEN
        String gameUuid = UUID.randomUUID().toString();
        PlayerScorePatchRequest player1 = PlayerScorePatchRequest.builder().playerName("player1").playerScore(20).build();
        PlayerScorePatchRequest player2 = PlayerScorePatchRequest.builder().playerName("player2").playerScore(20).build();
        PlayerScorePatchRequest player3 = PlayerScorePatchRequest.builder().playerName("player3").playerScore(20).build();
        Mockito.doThrow(new Exception()).when(playersScoreMapper).updatePlayerScore(gameUuid,"player3",20);
        Mockito.when(playersScoreMapper.getPlayersScoreOfGame(gameUuid))
                .thenReturn(List.of(
                        PlayerScore.builder().gameUuid(gameUuid).name("player1").score(10).build(),
                        PlayerScore.builder().gameUuid(gameUuid).name("player2").score(10).build(),
                        PlayerScore.builder().gameUuid(gameUuid).name("player3").score(10).build()
                ));
        Mockito.doNothing().when(playersScoreMapper).updatePlayerScore(gameUuid,"player1",10);
        Mockito.doNothing().when(playersScoreMapper).updatePlayerScore(gameUuid,"player2",10);

        //WHEN
        //THEN
        DataStoringException exception = Assert.assertThrows(DataStoringException.class, () -> playersScoreService.updatePlayersScore(gameUuid,List.of(player1,player2,player3)));
        Mockito.verify(playersScoreMapper).updatePlayerScore(gameUuid,"player1",20);
        Mockito.verify(playersScoreMapper).updatePlayerScore(gameUuid,"player2",20);
        Mockito.verify(playersScoreMapper).updatePlayerScore(gameUuid,"player3",20);
        Mockito.verify(playersScoreMapper).updatePlayerScore(gameUuid,"player1",10);
        Mockito.verify(playersScoreMapper).updatePlayerScore(gameUuid,"player2",10);
        Assertions.assertEquals("Error updating scores of players for gameId "+gameUuid,exception.getMessage());
    }

    @Test
    void updatePlayerScoreException() throws Exception {
        //GIVEN
        String gameUuid = UUID.randomUUID().toString();
        PlayerScorePatchRequest player1 = PlayerScorePatchRequest.builder().playerName("player1").playerScore(20).build();
        PlayerScorePatchRequest player2 = PlayerScorePatchRequest.builder().playerName("player2").playerScore(20).build();
        PlayerScorePatchRequest player3 = PlayerScorePatchRequest.builder().playerName("player3").playerScore(20).build();
        Mockito.doThrow(new Exception()).when(playersScoreMapper).updatePlayerScore(gameUuid,"player3",20);
        Mockito.when(playersScoreMapper.getPlayersScoreOfGame(gameUuid))
                .thenReturn(List.of(
                        PlayerScore.builder().gameUuid(gameUuid).name("player1").score(10).build(),
                        PlayerScore.builder().gameUuid(gameUuid).name("player2").score(10).build(),
                        PlayerScore.builder().gameUuid(gameUuid).name("player3").score(10).build()
                ));
        Mockito.doNothing().when(playersScoreMapper).updatePlayerScore(gameUuid,"player1",10);
        Mockito.doThrow(new Exception()).when(playersScoreMapper).updatePlayerScore(gameUuid,"player2",10);

        //WHEN
        //THEN
        DataStoringException exception = Assert.assertThrows(DataStoringException.class, () -> playersScoreService.updatePlayersScore(gameUuid,List.of(player1,player2,player3)));
        Mockito.verify(playersScoreMapper).updatePlayerScore(gameUuid,"player1",20);
        Mockito.verify(playersScoreMapper).updatePlayerScore(gameUuid,"player2",20);
        Mockito.verify(playersScoreMapper).updatePlayerScore(gameUuid,"player3",20);
        Mockito.verify(playersScoreMapper).updatePlayerScore(gameUuid,"player1",10);
        Mockito.verify(playersScoreMapper).updatePlayerScore(gameUuid,"player2",10);
        Assertions.assertEquals("Error when trying to rollback score update for player player2",exception.getMessage());
    }
}
