package com.wojtanowski.konrad.clubapp.club.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wojtanowski.konrad.clubapp.club.mapper.ClubMapper;
import com.wojtanowski.konrad.clubapp.club.model.dto.GetClubResponse;
import com.wojtanowski.konrad.clubapp.club.model.dto.GetClubsResponse;
import com.wojtanowski.konrad.clubapp.club.model.dto.PostClubRequest;
import com.wojtanowski.konrad.clubapp.club.model.entity.Club;
import com.wojtanowski.konrad.clubapp.club.repository.ClubRepository;
import com.wojtanowski.konrad.clubapp.club.service.ClubService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void testGetClubById() {
        Club club = clubRepository.findAll().get(0);

        ResponseEntity<GetClubResponse> dto = clubController.getClubById(club.getId());

        assertThat(dto.getBody()).isNotNull();
        assertThat(dto.getBody().getCity()).isEqualTo(club.getCity());
        assertThat(dto.getBody().getName()).isEqualTo(club.getName());
    }

    @Test
    void testGetClubByIdNotFound() {
        assertThrows(ResponseStatusException.class, () -> clubController.getClubById(UUID.randomUUID()));
    }

    @Test
    void testPostClub() {
        PostClubRequest club = PostClubRequest.builder()
                .city("c")
                .name("n")
                .build();

        ResponseEntity<GetClubResponse> response = clubController.postClub(club);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders()).isNotNull();

        String[] locationUUID = response.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Club found = clubRepository.findById(savedUUID).get();
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo(found.getName());
        assertThat(found.getCity()).isEqualTo(found.getCity());
    }
}