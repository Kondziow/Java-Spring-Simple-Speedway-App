package com.wojtanowski.konrad.clubapp.club.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wojtanowski.konrad.clubapp.club.model.dto.GetClubResponse;
import com.wojtanowski.konrad.clubapp.club.model.dto.GetClubsResponse;
import com.wojtanowski.konrad.clubapp.club.model.dto.PutClubRequest;
import com.wojtanowski.konrad.clubapp.club.model.entity.Club;
import com.wojtanowski.konrad.clubapp.club.service.ClubService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClubController.class)
class ClubControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ClubService clubService;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void testGetAllClubs() throws Exception {
        given(clubService.getAllClubs())
                .willReturn(GetClubsResponse.builder().clubs(Arrays.asList(getClubResponse1(), getClubResponse2())).build());

        mockMvc.perform(get(ClubController.CLUB_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.clubs").isArray())
                .andExpect(jsonPath("$.clubs.length()", is(2)));
    }

    @Test
    void testGetClubById() throws Exception {
        Club club = getClub1();
        GetClubResponse clubResponse = getClubResponse1();
        given(clubService.getClubById(any())).willReturn(Optional.of(clubResponse));

        mockMvc.perform(get(ClubController.CLUB_PATH_ID, club.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(club.getId().toString())))
                .andExpect(jsonPath("$.name", is(club.getName())))
                .andExpect(jsonPath("$.city", is(club.getCity())));
    }

    @Test
    void testGetClubByIdNotFound() throws Exception {
        given(clubService.getClubById(any())).willReturn(Optional.empty());

        mockMvc.perform(get(ClubController.CLUB_PATH_ID, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPostClub() throws Exception {
        Club club = getClub1();
        GetClubResponse clubResponse = getClubResponse1();
        given(clubService.saveNewClub(any())).willReturn(clubResponse);

        mockMvc.perform(post(ClubController.CLUB_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(club)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testPostClubNullNameNullCity() throws Exception {
        Club club = Club.builder().build();
        given(clubService.saveNewClub(any())).willReturn(getClubResponse1());

        mockMvc.perform(post(ClubController.CLUB_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(club)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPutClubById() throws Exception {
        GetClubResponse getClubResponse = getClubResponse1();
        PutClubRequest putClubRequest = getPutClubRequest1();
        given(clubService.updateClubById(any(), any())).willReturn(Optional.of(getClubResponse));

        mockMvc.perform(put(ClubController.CLUB_PATH_ID, getClubResponse.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(putClubRequest)))
                .andExpect(status().isNoContent())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.city", is(putClubRequest.getCity())))
                .andExpect(jsonPath("$.name", is(putClubRequest.getName())));
    }

    @Test
    void testPutClubByIdBadDto() throws Exception {
        Club club = Club.builder().build();
        given(clubService.saveNewClub(any())).willReturn(getClubResponse1());

        mockMvc.perform(put(ClubController.CLUB_PATH_ID, getClub1().getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(club)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPutClubByIdNotFound() throws Exception {
        PutClubRequest putClubRequest = getPutClubRequest1();
        given(clubService.updateClubById(any(), any())).willReturn(Optional.empty());

        mockMvc.perform(put(ClubController.CLUB_PATH_ID, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(putClubRequest)))
                .andExpect(status().isNotFound());
    }

    private Club getClub1() {
        return Club.builder()
                .id(UUID.fromString("06653105-74a1-443f-83db-531faeb027d8"))
                .city("ClubCity1")
                .name("ClubName1")
                .build();
    }

    private GetClubResponse getClubResponse1() {
        return GetClubResponse.builder()
                .id(UUID.fromString("06653105-74a1-443f-83db-531faeb027d8"))
                .city("ClubCity1")
                .name("ClubName1")
                .build();
    }

    private PutClubRequest getPutClubRequest1() {
        return PutClubRequest.builder()
                .city("ClubCity1")
                .name("ClubName1")
                .build();
    }

    private GetClubResponse getClubResponse2() {
        return GetClubResponse.builder()
                .id(UUID.fromString("9ed6dcfb-d972-4f2e-b3ba-5337295f5d1f"))
                .city("ClubCity2")
                .name("ClubName2")
                .build();
    }
}