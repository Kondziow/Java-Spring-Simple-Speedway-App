package com.wojtanowski.konrad.clubapp.club.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wojtanowski.konrad.clubapp.club.mapper.ClubMapper;
import com.wojtanowski.konrad.clubapp.club.model.dto.GetClubsResponse;
import com.wojtanowski.konrad.clubapp.club.repository.ClubRepository;
import com.wojtanowski.konrad.clubapp.club.service.ClubService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ClubControllerIT {
    @Autowired
    ClubController clubController;

    @Autowired
    ClubService clubService;

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    ClubMapper clubMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void testGetAllClubs() {
        GetClubsResponse clubs = clubController.getAllClubs().getBody();

        assertThat(clubs.getClubs()).isNotNull();
        assertThat(clubs.getClubs().size()).isEqualTo(8);
    }
}