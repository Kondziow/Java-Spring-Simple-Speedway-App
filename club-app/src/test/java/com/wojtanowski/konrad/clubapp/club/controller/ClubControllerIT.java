package com.wojtanowski.konrad.clubapp.club.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wojtanowski.konrad.clubapp.club.mapper.ClubMapper;
import com.wojtanowski.konrad.clubapp.club.model.dto.GetClubResponse;
import com.wojtanowski.konrad.clubapp.club.model.dto.GetClubsResponse;
import com.wojtanowski.konrad.clubapp.club.model.dto.PostClubRequest;
import com.wojtanowski.konrad.clubapp.club.model.dto.PutClubRequest;
import com.wojtanowski.konrad.clubapp.club.model.entity.Club;
import com.wojtanowski.konrad.clubapp.club.repository.ClubRepository;
import com.wojtanowski.konrad.clubapp.club.service.ClubService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
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

    @Rollback
    @Transactional
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
        assertThat(found.getName()).isEqualTo(club.getName());
        assertThat(found.getCity()).isEqualTo(club.getCity());
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateClubById() {
        Club club = clubRepository.findAll().get(0);
        PutClubRequest putClubRequest = PutClubRequest.builder()
                .city(club.getCity())
                .name("UPDATED")
                .build();

        ResponseEntity<GetClubResponse> responseEntity = clubController.putClubById(club.getId(), putClubRequest);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Club updatedClub = clubRepository.findById(club.getId()).get();
        assertThat(updatedClub.getName()).isEqualTo("UPDATED");
        assertThat(updatedClub.getCity()).isEqualTo(club.getCity());
    }

    @Test
    void testUpdateClubByIdBadDto() {
        Club club = clubRepository.findAll().get(0);
        PutClubRequest putClubRequest = PutClubRequest.builder().build();
        assertThrows(ConstraintViolationException.class, () -> clubController.putClubById(club.getId(), putClubRequest));
    }

    @Test
    void testUpdateClubByIdNotFound() {
        PutClubRequest putClubRequest = PutClubRequest.builder()
                .city("c")
                .name("n")
                .build();
        assertThrows(ResponseStatusException.class, () -> clubController.putClubById(UUID.randomUUID(), putClubRequest));
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteClubById() {
        Club club = clubRepository.findAll().get(0);

        ResponseEntity<Void> response = clubController.deleteClubById(club.getId());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        assertThat(clubRepository.findById(club.getId())).isEmpty();
    }

    @Test
    void testDeleteClubByIdNotFound() {
        assertThrows(ResponseStatusException.class, () -> clubController.deleteClubById(UUID.randomUUID()));
    }
}