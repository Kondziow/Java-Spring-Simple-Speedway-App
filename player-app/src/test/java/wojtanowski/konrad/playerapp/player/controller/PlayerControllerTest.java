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
import wojtanowski.konrad.playerapp.player.model.entity.Player;
import wojtanowski.konrad.playerapp.player.service.api.PlayerService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
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
                    .andExpect(jsonPath("$.players.length()",is(2)));
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

    private Player getPlayer2() {
        return Player.builder()
                .id(UUID.fromString("f40e916b-2caf-47e8-b826-cc20d899b3ce"))
                .name("PlayerName2")
                .surname("PlayerSurname2")
                .birthDate(LocalDate.of(2001, 11, 11))
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