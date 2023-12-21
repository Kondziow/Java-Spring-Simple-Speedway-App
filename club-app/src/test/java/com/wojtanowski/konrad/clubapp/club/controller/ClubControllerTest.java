package com.wojtanowski.konrad.clubapp.club.controller;

import com.wojtanowski.konrad.clubapp.club.mapper.ClubMapper;
import com.wojtanowski.konrad.clubapp.club.model.entity.Club;
import com.wojtanowski.konrad.clubapp.club.service.ClubService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClubController.class)
class ClubControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ClubService clubService;

    @MockBean
    ClubMapper clubMapper;


    @Test
    void testGetAllClubs() throws Exception {
        given(clubService.getAllClubs())
                .willReturn(Arrays.asList(getClub1(), getClub2()));

        mockMvc.perform(get(ClubController.CLUB_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.clubs").isArray())
                .andExpect(jsonPath("$.clubs.length()", is(2)));

    }

    private Club getClub1() {
        return Club.builder()
                .id(UUID.randomUUID())
                .city("ClubCity1")
                .name("ClubName1")
                .build();
    }

    private Club getClub2() {
        return Club.builder()
                .id(UUID.randomUUID())
                .city("ClubCity2")
                .name("ClubName2")
                .build();
    }
}