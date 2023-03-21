package com.sulwep7.tarotpointcounterback.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sulwep7.tarotpointcounterback.model.dto.GamePostRequest;
import com.sulwep7.tarotpointcounterback.model.entity.GameWDetails;
import com.sulwep7.tarotpointcounterback.model.exception.DataStoringException;
import com.sulwep7.tarotpointcounterback.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GameService gameService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getGamesOK() throws Exception {
        //GIVEN
        Mockito.when(gameService.getGames()).thenReturn(new ArrayList<>());

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/games")
                .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        //THEN
        Mockito.verify(gameService).getGames();
    }

    @Test
    void getGamesNotFound() throws Exception {
        //GIVEN
        Mockito.when(gameService.getGames()).thenReturn(null);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/games")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        //THEN
        Mockito.verify(gameService).getGames();
    }

    @Test
    void getAllGamesWDetailsOK() throws Exception{
        //GIVEN
        String gameUuid = UUID.randomUUID().toString();
        int nrPlayers = 3;
        GameWDetails player1 = GameWDetails.builder()
                .gameUuid(gameUuid)
                .nrPlayers(nrPlayers)
                .playerName("player1")
                .playerScore(10)
                .build();
        GameWDetails player2 = GameWDetails.builder()
                .gameUuid(gameUuid)
                .nrPlayers(nrPlayers)
                .playerName("player2")
                .playerScore(10)
                .build();
        GameWDetails player3 = GameWDetails.builder()
                .gameUuid(gameUuid)
                .nrPlayers(nrPlayers)
                .playerName("player3")
                .playerScore(10)
                .build();
        List<GameWDetails> gameWDetailsList = List.of(player1,player2,player3);
        Map<String, List<GameWDetails>> games = new HashMap<>();
        games.put(gameUuid,gameWDetailsList);

        Mockito.when(gameService.getAllGamesWDetails()).thenReturn(games);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/gamesWDetails")
                    .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.games[0].gameUuid").value(gameUuid))
                .andExpect(MockMvcResultMatchers.jsonPath("$.games[0].nrPlayers").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.games[0].players[0].playerName").value("player1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.games[0].players[0].playerScore").value(10));

        //THEN
        Mockito.verify(gameService).getAllGamesWDetails();
    }

    @Test
    void getAllGamesWDetailsNotFound() throws Exception {
        //GIVEN
        Mockito.when(gameService.getAllGamesWDetails()).thenReturn(null);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/gamesWDetails")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        //THEN
        Mockito.verify(gameService).getAllGamesWDetails();
    }

    @Test
    void insertGameCreated() throws Exception{
        //GIVEN
        UUID gameUuid = UUID.randomUUID();
        Mockito.when(gameService.insertNewGame(4)).thenReturn(gameUuid);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/game")
                        .accept("application/json")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new GamePostRequest(4))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(gameUuid.toString()));

        //THEN
        Mockito.verify(gameService).insertNewGame(4);
    }
    @Test()
    void insertGameInternalServerError() throws Exception{
        //GIVEN
        UUID gameUuid = UUID.randomUUID();
        Mockito.when(gameService.insertNewGame(4)).thenThrow(new DataStoringException("Error"));

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/game")
                        .accept("application/json")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new GamePostRequest(4))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());

        //THEN
        Mockito.verify(gameService).insertNewGame(4);
    }
}
