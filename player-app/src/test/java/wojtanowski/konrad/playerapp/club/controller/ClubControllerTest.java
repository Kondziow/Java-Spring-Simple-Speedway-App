package wojtanowski.konrad.playerapp.club.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import wojtanowski.konrad.playerapp.club.model.entity.Club;
import wojtanowski.konrad.playerapp.club.model.model.GetClubResponse;
import wojtanowski.konrad.playerapp.club.service.api.ClubService;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClubController.class)
class ClubControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ClubService clubService;

    @Autowired
    ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Test
    void testPostClub() throws Exception {
        Club club = Club.builder().build();
        GetClubResponse clubResponse = GetClubResponse.builder().build();
        given(clubService.saveNewClub(any())).willReturn(clubResponse);

        mockMvc.perform(post(ClubController.CLUB_PATH_ID, UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(club)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testDeleteClubById() throws Exception {
        given(clubService.deleteClubById(any())).willReturn(true);

        UUID id = UUID.randomUUID();

        mockMvc.perform(delete(ClubController.CLUB_PATH_ID, id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(clubService).deleteClubById(uuidArgumentCaptor.capture());

        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(id);
    }

    @Test
    void testDeleteClubByIdNoptFound() throws Exception {
        given(clubService.deleteClubById(any())).willReturn(false);

        mockMvc.perform(delete(ClubController.CLUB_PATH_ID, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}