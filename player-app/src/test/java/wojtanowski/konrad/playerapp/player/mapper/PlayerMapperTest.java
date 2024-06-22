package wojtanowski.konrad.playerapp.player.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wojtanowski.konrad.playerapp.club.model.entity.Club;
import wojtanowski.konrad.playerapp.club.model.model.GetClubResponse;
import wojtanowski.konrad.playerapp.player.model.dto.GetPlayerResponse;
import wojtanowski.konrad.playerapp.player.model.dto.PostPlayerRequest;
import wojtanowski.konrad.playerapp.player.model.dto.PutPlayerRequest;
import wojtanowski.konrad.playerapp.player.model.entity.Player;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlayerMapperTest {
    @Autowired
    PlayerMapper playerMapper;

    Player player;
    PutPlayerRequest putPlayerRequest;
    PostPlayerRequest postPlayerRequest;

    @BeforeEach
    void setUp() {
        player = Player.builder()
                .name("n1")
                .surname("s2")
                .birthDate(LocalDate.of(2000, 10, 10))
                .club(Club.builder().id(UUID.randomUUID()).build())
                .build();

        putPlayerRequest = PutPlayerRequest.builder()
                .name("n2")
                .surname("s2")
                .birthDate(LocalDate.of(2001, 11, 11))
                .club(GetClubResponse.builder().id(UUID.randomUUID()).build())
                .build();

        postPlayerRequest = PostPlayerRequest.builder()
                .name("n3")
                .surname("s3")
                .birthDate(LocalDate.of(2002, 12, 12))
                .build();
    }

    @Test
    void testPlayerToGetPlayerResponse() {
        GetPlayerResponse mapped = playerMapper.playerToGetPlayerResponse(player);
        
        assertEquals(mapped.getId(), player.getId());
        assertEquals(mapped.getName(), player.getName());
        assertEquals(mapped.getSurname(), player.getSurname());
        assertEquals(mapped.getBirthDate(), player.getBirthDate());
    }

    @Test
    void testPutPlayerRequestToPlayer() {
        Player mapped = playerMapper.putPlayerRequestToPlayer(putPlayerRequest);

        assertEquals(mapped.getName(), putPlayerRequest.getName());
        assertEquals(mapped.getSurname(), putPlayerRequest.getSurname());
        assertEquals(mapped.getBirthDate(), putPlayerRequest.getBirthDate());
        assertEquals(mapped.getClub().getId(), putPlayerRequest.getClub().getId());
    }

    @Test
    void testPostPlayerRequestToPlayer() {
        Player mapped = playerMapper.postPlayerRequestToPlayer(postPlayerRequest);

        assertEquals(mapped.getName(), postPlayerRequest.getName());
        assertEquals(mapped.getSurname(), postPlayerRequest.getSurname());
        assertEquals(mapped.getBirthDate(), postPlayerRequest.getBirthDate());
    }
}