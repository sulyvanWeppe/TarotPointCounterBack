package com.sulwep7.tarotpointcounterback.unit.controller;

import com.sulwep7.tarotpointcounterback.mapper.GameMapper;
import com.sulwep7.tarotpointcounterback.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.ServletContext;
import java.util.ArrayList;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GameService gameService;

    @Test
    public void getGames() throws Exception {
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
}
