package com.concertticketing.api.apis.concerts.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ConcertControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getConcerts_ok() throws Exception {
        // save 데이터로 비교
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/concerts?page=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.concerts").isArray());
    }

    @Test
    void getConcert_ok() throws Exception {
        // save 데이터로 비교
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/concerts/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getConcert_bad_request() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/concerts/a"))
                .andExpect(status().isBadRequest());
    }
}
