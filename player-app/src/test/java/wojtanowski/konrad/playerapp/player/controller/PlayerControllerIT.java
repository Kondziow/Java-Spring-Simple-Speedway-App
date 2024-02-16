package wojtanowski.konrad.playerapp.player.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import wojtanowski.konrad.playerapp.player.mapper.PlayerMapper;
import wojtanowski.konrad.playerapp.player.model.dto.GetPlayerResponse;
import wojtanowski.konrad.playerapp.player.model.dto.GetPlayersResponse;
import wojtanowski.konrad.playerapp.player.model.dto.PostPlayerRequest;
import wojtanowski.konrad.playerapp.player.model.dto.PutPlayerRequest;
import wojtanowski.konrad.playerapp.player.model.entity.Player;
import wojtanowski.konrad.playerapp.player.repository.PlayerRepository;
import wojtanowski.konrad.playerapp.player.service.api.PlayerService;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PlayerControllerIT {
    @Autowired
    PlayerController playerController;

    @Autowired
    PlayerService playerService;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PlayerMapper playerMapper;

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
    void testGetALlPlayers() {
        GetPlayersResponse players = playerController.getAllPlayers().getBody();

        assertThat(players).isNotNull();
        assertThat(players.getPlayers()).isNotNull();
        assertThat(players.getPlayers().size()).isEqualTo(4);
    }

    @Test
    void testGetPlayerById() {
        Player player = playerRepository.findAll().get(0);

        ResponseEntity<GetPlayerResponse> dto = playerController.getPlayerById(player.getId());

        assertThat(dto.getBody()).isNotNull();
        assertThat(dto.getBody().getName()).isEqualTo(player.getName());
        assertThat(dto.getBody().getSurname()).isEqualTo(player.getSurname());
        assertThat(dto.getBody().getBirthDate()).isEqualTo(player.getBirthDate());
    }

    @Test
    void testGetPlayerByIdNotFound() {
        assertThrows(ResponseStatusException.class, () -> playerController.getPlayerById(UUID.randomUUID()));
    }

    @Rollback
    @Transactional
    @Test
    void testPostPlayer() {
        PostPlayerRequest player = PostPlayerRequest.builder()
                .name("n")
                .surname("s")
                .birthDate(LocalDate.of(2000, 10, 10))
                .build();

        ResponseEntity<GetPlayerResponse> response = playerController.postPlayer(player);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders()).isNotNull();

        assertThat(response.getHeaders().getLocation()).isNotNull();
        String[] locationUUID = response.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Player found = playerRepository.findById(savedUUID).get();
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo(player.getName());
        assertThat(found.getSurname()).isEqualTo(player.getSurname());
        assertThat(found.getBirthDate()).isEqualTo(player.getBirthDate());
    }

    @Test
    void testPostPlayerNullName() {
        PostPlayerRequest postPlayerRequest = PostPlayerRequest.builder()
                .surname("s")
                .birthDate(LocalDate.of(2000, 10, 10))
                .build();
        assertThrows(ConstraintViolationException.class, () -> playerController.postPlayer(postPlayerRequest));
    }

    @Test
    void testPostPlayerNullSurname() {
        PostPlayerRequest postPlayerRequest = PostPlayerRequest.builder()
                .name("n")
                .birthDate(LocalDate.of(2000, 10, 10))
                .build();
        assertThrows(ConstraintViolationException.class, () -> playerController.postPlayer(postPlayerRequest));
    }

    @Test
    void testPostPlayerTooLongName() {
        PostPlayerRequest postPlayerRequest = PostPlayerRequest.builder()
                .name("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn")
                .surname("s")
                .birthDate(LocalDate.of(2000, 10, 10))
                .build();
        assertThrows(ConstraintViolationException.class, () -> playerController.postPlayer(postPlayerRequest));
    }

    @Test
    void testPostPlayerTooLongSurname() {
        PostPlayerRequest postPlayerRequest = PostPlayerRequest.builder()
                .name("n")
                .surname("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss")
                .birthDate(LocalDate.of(2000, 10, 10))
                .build();
        assertThrows(ConstraintViolationException.class, () -> playerController.postPlayer(postPlayerRequest));
    }

    @Rollback
    @Transactional
    @Test
    void testUpdatePlayerById() {
        Player player = playerRepository.findAll().get(0);
        PutPlayerRequest putPlayerRequest = PutPlayerRequest.builder()
                .name("UPDATED")
                .surname(player.getSurname())
                .birthDate(player.getBirthDate())
                .build();

        ResponseEntity<GetPlayerResponse> responseEntity = playerController.putPlayerById(player.getId(), putPlayerRequest);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Player updatedPlayer = playerRepository.findById(player.getId()).get();
        assertThat(updatedPlayer.getName()).isEqualTo("UPDATED");
        assertThat(updatedPlayer.getSurname()).isEqualTo(player.getSurname());
        assertThat(updatedPlayer.getBirthDate()).isEqualTo(player.getBirthDate());
    }

    @Test
    void testUpdatePlayerByIdNullName() {
        Player player = playerRepository.findAll().get(0);
        PutPlayerRequest putPlayerRequest = PutPlayerRequest.builder()
                .surname("s")
                .birthDate(LocalDate.of(2000, 10, 10))
                .build();

        assertThrows(ConstraintViolationException.class, () -> playerController.putPlayerById(player.getId(), putPlayerRequest));
    }

    @Test
    void testUpdatePlayerByIdNullSurname() {
        Player player = playerRepository.findAll().get(0);
        PutPlayerRequest putPlayerRequest = PutPlayerRequest.builder()
                .name("n")
                .birthDate(LocalDate.of(2000, 10, 10))
                .build();

        assertThrows(ConstraintViolationException.class, () -> playerController.putPlayerById(player.getId(), putPlayerRequest));
    }

    @Test
    void testUpdatePlayerByIdTooLongName() {
        Player player = playerRepository.findAll().get(0);
        PutPlayerRequest putPlayerRequest = PutPlayerRequest.builder()
                .name("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn")
                .surname("s")
                .birthDate(LocalDate.of(2000, 10, 10))
                .build();

        assertThrows(ConstraintViolationException.class, () -> playerController.putPlayerById(player.getId(), putPlayerRequest));
    }

    @Test
    void testUpdatePlayerByIdTooLongSurname() {
        Player player = playerRepository.findAll().get(0);
        PutPlayerRequest putPlayerRequest = PutPlayerRequest.builder()
                .name("n")
                .surname("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss")
                .birthDate(LocalDate.of(2000, 10, 10))
                .build();

        assertThrows(ConstraintViolationException.class, () -> playerController.putPlayerById(player.getId(), putPlayerRequest));
    }

    @Test
    void testUpdatePlayerByIdNotFound() {
        PutPlayerRequest putPlayerRequest = PutPlayerRequest.builder()
                .name("n")
                .surname("s")
                .birthDate(LocalDate.of(2000, 10, 10))
                .build();

        assertThrows(ResponseStatusException.class, () -> playerController.putPlayerById(UUID.randomUUID(), putPlayerRequest));
    }

    @Rollback
    @Transactional
    @Test
    void testDeletePlayerById() {
        Player player = playerRepository.findAll().get(0);

        ResponseEntity<Void> response = playerController.deletePlayerById(player.getId());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        assertThat(playerRepository.findById(player.getId())).isEmpty();
    }

    @Test
    void testDeletePlayerByIdNotFound() {
        assertThrows(ResponseStatusException.class, () ->playerController.deletePlayerById(UUID.randomUUID()));
    }
}