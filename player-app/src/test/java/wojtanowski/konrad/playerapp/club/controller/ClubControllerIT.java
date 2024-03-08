package wojtanowski.konrad.playerapp.club.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wojtanowski.konrad.playerapp.club.model.entity.Club;
import wojtanowski.konrad.playerapp.club.model.model.PostClubRequest;
import wojtanowski.konrad.playerapp.club.repository.ClubRepository;
import wojtanowski.konrad.playerapp.club.service.api.ClubService;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClubControllerIT {
    @Autowired
    ClubController clubController;

    @Autowired
    ClubService clubService;

    @Autowired
    ClubRepository clubRepository;

    @Rollback
    @Transactional
    @Test
    void testPostClub() {
        PostClubRequest club = PostClubRequest.builder().build();

        ResponseEntity<Void> response = clubController.postClub(UUID.randomUUID(), club);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders()).isNotNull();

        assertThat(response.getHeaders().getLocation()).isNotNull();
        String[] locationUUID = response.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Club found = clubRepository.findById(savedUUID).get();
        assertThat(found).isNotNull();
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