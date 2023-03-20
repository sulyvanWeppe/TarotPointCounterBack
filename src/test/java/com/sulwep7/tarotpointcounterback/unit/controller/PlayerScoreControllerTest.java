package com.sulwep7.tarotpointcounterback.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sulwep7.tarotpointcounterback.model.dto.PlayerScorePatchRequest;
import com.sulwep7.tarotpointcounterback.model.dto.PlayerScorePostRequest;
import com.sulwep7.tarotpointcounterback.model.dto.PlayersScorePatchRequest;
import com.sulwep7.tarotpointcounterback.model.dto.PlayersScorePostRequest;
import com.sulwep7.tarotpointcounterback.model.entity.PlayerScore;
import com.sulwep7.tarotpointcounterback.model.exception.DataStoringException;
import com.sulwep7.tarotpointcounterback.service.PlayersScoreService;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class PlayerScoreControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PlayersScoreService playersScoreService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void insertPlayerScoreCreated() throws Exception{
        //GIVEN
        String gameUuid = UUID.randomUUID().toString();
        String playerName = "player";
        int playerScore = 10;

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/score")
                    .accept("application/json")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(PlayerScore.builder()
                            .name(playerName)
                            .score(playerScore)
                            .gameUuid(gameUuid)
                            .build())))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        //THEN
        Mockito.verify(playersScoreService).insertPlayerScore(gameUuid,playerName,playerScore);
    }

    @Test
    void insertPlayerScoreInternalServerError() throws Exception {
        //GIVEN
        String gameUuid = UUID.randomUUID().toString();
        String playerName = "player";
        int playerScore = 10;
        Mockito.doThrow(new DataStoringException("Error")).when(playersScoreService).insertPlayerScore(gameUuid,playerName,playerScore);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/score")
                        .accept("application/json")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(PlayerScore.builder()
                                .name(playerName)
                                .score(playerScore)
                                .gameUuid(gameUuid)
                                .build())))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());

        //THEN
        Mockito.verify(playersScoreService).insertPlayerScore(gameUuid,playerName,playerScore);
    }

    @Test
    void insertPlayersScoreCreated() throws Exception{
        //GIVEN
        String gameUuid = UUID.randomUUID().toString();
        PlayerScorePostRequest player1 = PlayerScorePostRequest.builder()
                .playerName("player1")
                .playerScore(10)
                .build();
        PlayerScorePostRequest player2 = PlayerScorePostRequest.builder()
                .playerName("player2")
                .playerScore(10)
                .build();
        PlayerScorePostRequest player3 = PlayerScorePostRequest.builder()
                .playerName("player3")
                .playerScore(10)
                .build();
        PlayersScorePostRequest request = PlayersScorePostRequest.builder()
                .playerScorePostRequestList(List.of(player1,player2,player3))
                .gameUuid(gameUuid)
                .build();

        Mockito.doNothing().when(playersScoreService).insertPlayersScore(gameUuid,List.of(player1,player2,player3));

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/scores")
                    .accept("application/json")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        //THEN
        Mockito.verify(playersScoreService).insertPlayersScore(ArgumentMatchers.anyString(), ArgumentMatchers.any(List.class));
    }

    @Test
    void insertPlayersScoreInternalServerError() throws Exception{
        //GIVEN
        String gameUuid = UUID.randomUUID().toString();
        PlayerScorePostRequest player1 = PlayerScorePostRequest.builder()
                .playerName("player1")
                .playerScore(10)
                .build();
        PlayerScorePostRequest player2 = PlayerScorePostRequest.builder()
                .playerName("player2")
                .playerScore(10)
                .build();
        PlayerScorePostRequest player3 = PlayerScorePostRequest.builder()
                .playerName("player3")
                .playerScore(10)
                .build();
        PlayersScorePostRequest request = PlayersScorePostRequest.builder()
                .playerScorePostRequestList(List.of(player1,player2,player3))
                .gameUuid(gameUuid)
                .build();

        Mockito.doThrow(new DataStoringException("Error")).when(playersScoreService).insertPlayersScore(ArgumentMatchers.anyString(),ArgumentMatchers.any(List.class));

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/scores")
                        .accept("application/json")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());

        //THEN
        Mockito.verify(playersScoreService).insertPlayersScore(ArgumentMatchers.anyString(), ArgumentMatchers.any(List.class));
    }

    @Test
    void updatePlayersScoreOK() throws Exception{
        //GIVEN
        String gameUuid = UUID.randomUUID().toString();
        PlayerScorePatchRequest player1 = PlayerScorePatchRequest.builder()
                .playerName("player1")
                .playerScore(10)
                .build();
        PlayerScorePatchRequest player2 = PlayerScorePatchRequest.builder()
                .playerName("player2")
                .playerScore(10)
                .build();
        PlayerScorePatchRequest player3 = PlayerScorePatchRequest.builder()
                .playerName("player3")
                .playerScore(10)
                .build();
        PlayersScorePatchRequest request = PlayersScorePatchRequest.builder()
                .playerScorePatchRequests(List.of(player1,player2,player3))
                .gameUuid(gameUuid)
                .build();

        Mockito.doNothing().when(playersScoreService).updatePlayersScore(gameUuid,List.of(player1,player2,player3));

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.patch("/scores")
                        .accept("application/json")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //THEN
        Mockito.verify(playersScoreService).updatePlayersScore(ArgumentMatchers.anyString(), ArgumentMatchers.any(List.class));
    }

    @Test
    void updatePlayersScoreInternalServerError() throws Exception{
        //GIVEN
        String gameUuid = UUID.randomUUID().toString();
        PlayerScorePatchRequest player1 = PlayerScorePatchRequest.builder()
                .playerName("player1")
                .playerScore(10)
                .build();
        PlayerScorePatchRequest player2 = PlayerScorePatchRequest.builder()
                .playerName("player2")
                .playerScore(10)
                .build();
        PlayerScorePatchRequest player3 = PlayerScorePatchRequest.builder()
                .playerName("player3")
                .playerScore(10)
                .build();
        PlayersScorePatchRequest request = PlayersScorePatchRequest.builder()
                .playerScorePatchRequests(List.of(player1,player2,player3))
                .gameUuid(gameUuid)
                .build();

        Mockito.doThrow(new DataStoringException("Error")).when(playersScoreService).updatePlayersScore(ArgumentMatchers.anyString(), ArgumentMatchers.any(List.class));

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.patch("/scores")
                        .accept("application/json")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());

        //THEN
        Mockito.verify(playersScoreService).updatePlayersScore(ArgumentMatchers.anyString(), ArgumentMatchers.any(List.class));
    }
}
