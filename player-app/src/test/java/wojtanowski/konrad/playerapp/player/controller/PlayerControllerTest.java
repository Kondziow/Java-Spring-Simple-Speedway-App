package wojtanowski.konrad.playerapp.player.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import wojtanowski.konrad.playerapp.player.model.dto.GetPlayerResponse;
import wojtanowski.konrad.playerapp.player.model.dto.GetPlayersResponse;
import wojtanowski.konrad.playerapp.player.model.dto.PutPlayerRequest;
import wojtanowski.konrad.playerapp.player.model.entity.Player;
import wojtanowski.konrad.playerapp.player.service.api.PlayerService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PlayerController.class)
class PlayerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    PlayerService playerService;

    @Autowired
    ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Test
    void testGetAllPlayers() throws Exception {
        given(playerService.getAllPlayers())
                .willReturn(GetPlayersResponse.builder().players(Arrays.asList(getGetPlayerResponse1(), getGetPlayerResponse2())).build());

        mockMvc.perform(get(PlayerController.PLAYER_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.players").isArray())
                .andExpect(jsonPath("$.players.length()", is(2)));
    }

    @Test
    void testGetPlayerById() throws Exception {
        Player player = getPlayer1();
        GetPlayerResponse playerResponse = getGetPlayerResponse1();
        given(playerService.getPlayerById(any())).willReturn(Optional.of(playerResponse));

        mockMvc.perform(get(PlayerController.PLAYER_PATH_ID, player.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(player.getId().toString())))
                .andExpect(jsonPath("$.name", is(player.getName())))
                .andExpect(jsonPath("$.surname", is(player.getSurname())))
                .andExpect(jsonPath("$.birthDate", is(player.getBirthDate().toString())));
    }

    @Test
    void testGetPlayerIdNotFound() throws Exception {
        given(playerService.getPlayerById(any())).willReturn(Optional.empty());

        mockMvc.perform(get(PlayerController.PLAYER_PATH_ID, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPostPlayer() throws Exception {
        Player player = getPlayer1();
        GetPlayerResponse playerResponse = getGetPlayerResponse1();
        given(playerService.saveNewPlayer(any())).willReturn(playerResponse);

        mockMvc.perform(post(PlayerController.PLAYER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testPostPlayerNullName() throws Exception {
        Player player = Player.builder()
                .surname("s")
                .birthDate(LocalDate.of(2000, 10, 10))
                .build();
        given(playerService.saveNewPlayer(any())).willReturn(getGetPlayerResponse1());

        mockMvc.perform(post(PlayerController.PLAYER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPostPlayerNullSurname() throws Exception {
        Player player = Player.builder()
                .name("n")
                .birthDate(LocalDate.of(2000, 10, 10))
                .build();
        given(playerService.saveNewPlayer(any())).willReturn(getGetPlayerResponse1());

        mockMvc.perform(post(PlayerController.PLAYER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPostPlayerTooLongName() throws Exception {
        Player player = Player.builder()
                .name("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn")
                .surname("s")
                .birthDate(LocalDate.of(2000, 10, 10))
                .build();
        given(playerService.saveNewPlayer(any())).willReturn(getGetPlayerResponse1());

        mockMvc.perform(post(PlayerController.PLAYER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPostPlayerTooLongSurname() throws Exception {
        Player player = Player.builder()
                .name("n")
                .surname("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss")
                .birthDate(LocalDate.of(2000, 10, 10))
                .build();
        given(playerService.saveNewPlayer(any())).willReturn(getGetPlayerResponse1());

        mockMvc.perform(post(PlayerController.PLAYER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPutPlayerById() throws Exception {
        GetPlayerResponse getPlayerResponse = getGetPlayerResponse1();
        PutPlayerRequest putPlayerRequest = getPutPlayerRequest1();
        given(playerService.updatePlayerById(any(), any())).willReturn(Optional.of(getPlayerResponse));

        mockMvc.perform(put(PlayerController.PLAYER_PATH_ID, getPlayerResponse.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(putPlayerRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(putPlayerRequest.getName())))
                .andExpect(jsonPath("$.surname", is(putPlayerRequest.getSurname())))
                .andExpect(jsonPath("$.birthDate", is(putPlayerRequest.getBirthDate().toString())));
    }

    @Test
    void testPutPlayerByIdNullName() throws Exception {
        Player player = Player.builder()
                .surname("s")
                .birthDate(LocalDate.of(2000, 10, 10))
                .build();
        given(playerService.saveNewPlayer(any())).willReturn(getGetPlayerResponse1());

        mockMvc.perform(put(PlayerController.PLAYER_PATH_ID, getPlayer1().getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPutPlayerByIdNullSurname() throws Exception {
        Player player = Player.builder()
                .name("n")
                .birthDate(LocalDate.of(2000, 10, 10))
                .build();
        given(playerService.saveNewPlayer(any())).willReturn(getGetPlayerResponse1());

        mockMvc.perform(put(PlayerController.PLAYER_PATH_ID, getPlayer1().getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPutPlayerByIdTooLongName() throws Exception {
        Player player = Player.builder()
                .name("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn")
                .surname("s")
                .birthDate(LocalDate.of(2000, 10, 10))
                .build();
        given(playerService.saveNewPlayer(any())).willReturn(getGetPlayerResponse1());

        mockMvc.perform(put(PlayerController.PLAYER_PATH_ID, getPlayer1().getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPutPlayerByIdTooLongSurname() throws Exception {
        Player player = Player.builder()
                .name("n")
                .surname("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss")
                .birthDate(LocalDate.of(2000, 10, 10))
                .build();
        given(playerService.saveNewPlayer(any())).willReturn(getGetPlayerResponse1());

        mockMvc.perform(put(PlayerController.PLAYER_PATH_ID, getPlayer1().getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPutPlayerByIdNotFound() throws Exception {
        PutPlayerRequest putPlayerRequest = getPutPlayerRequest1();
        given(playerService.updatePlayerById(any(), any())).willReturn(Optional.empty());

        mockMvc.perform(put(PlayerController.PLAYER_PATH_ID, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(putPlayerRequest)))
                .andExpect(status().isNotFound());
    }

    private Player getPlayer1() {
        return Player.builder()
                .id(UUID.fromString("4b708f9a-9328-41c1-84a3-88f5a956b35c"))
                .name("PlayerName1")
                .surname("PlayerSurname1")
                .birthDate(LocalDate.of(2000, 10, 10))
                .build();
    }

    private GetPlayerResponse getGetPlayerResponse1() {
        return GetPlayerResponse.builder()
                .id(UUID.fromString("4b708f9a-9328-41c1-84a3-88f5a956b35c"))
                .name("PlayerName1")
                .surname("PlayerSurname1")
                .birthDate(LocalDate.of(2000, 10, 10))
                .build();
    }

    private PutPlayerRequest getPutPlayerRequest1() {
        return PutPlayerRequest.builder()
                .name("PlayerName1")
                .surname("PlayerSurname1")
                .birthDate(LocalDate.of(2000, 10, 10))
                .build();
    }

    private GetPlayerResponse getGetPlayerResponse2() {
        return GetPlayerResponse.builder()
                .id(UUID.fromString("f40e916b-2caf-47e8-b826-cc20d899b3ce"))
                .name("PlayerName2")
                .surname("PlayerSurname2")
                .birthDate(LocalDate.of(2001, 11, 11))
                .build();
    }
}