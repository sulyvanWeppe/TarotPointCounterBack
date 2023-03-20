package com.sulwep7.tarotpointcounterback.unit.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class PingControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void ping() throws Exception{
        //GIVEN

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/ping")
                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //THEN
    }
}
