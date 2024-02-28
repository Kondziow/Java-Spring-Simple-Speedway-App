package wojtanowski.konrad.playerapp.club.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wojtanowski.konrad.playerapp.club.model.entity.Club;
import wojtanowski.konrad.playerapp.club.model.model.GetClubResponse;
import wojtanowski.konrad.playerapp.club.model.model.PostClubRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClubMapperTest {
    @Autowired
    ClubMapper clubMapper;

    Club club;

    PostClubRequest postClubRequest;

    @BeforeEach
    void setUp() {
        club = Club.builder().build();
        postClubRequest = PostClubRequest.builder().build();
    }

    @Test
    void testPostClubRequestToClub() {
        Club mapped = clubMapper.postClubRequestToClub(postClubRequest);

        assertEquals(mapped.getPlayers(), postClubRequest.getPlayers());
    }

    @Test
    void testClubToGetClubResponse() {
        GetClubResponse mapped = clubMapper.clubToGetClubResponse(club);

        assertEquals(mapped.getId(), club.getId());
    }
}