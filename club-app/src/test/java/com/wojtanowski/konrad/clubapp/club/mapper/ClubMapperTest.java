package com.wojtanowski.konrad.clubapp.club.mapper;

import com.wojtanowski.konrad.clubapp.club.model.dto.GetClubResponse;
import com.wojtanowski.konrad.clubapp.club.model.dto.PutClubRequest;
import com.wojtanowski.konrad.clubapp.club.model.entity.Club;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClubMapperTest {
    @Autowired
    ClubMapper clubMapper;

    Club club;
    PutClubRequest putClubRequest;

    @BeforeEach
    void setUp() {
        club = Club.builder()
                .id(UUID.randomUUID())
                .city("c1")
                .name("n1")
                .build();

        putClubRequest = PutClubRequest.builder()
                .city("c2")
                .name("n2")
                .build();
    }

    @Test
    void testClubToGetClubResponse() {
        GetClubResponse mapped = clubMapper.clubToGetClubResponse(club);

        assertEquals(mapped.getId(), club.getId());
        assertEquals(mapped.getCity(), club.getCity());
        assertEquals(mapped.getName(), club.getName());
    }

    @Test
    void testPutClubRequestToClub() {
        Club mapped = clubMapper.putClubRequestToClub(putClubRequest);

        assertEquals(mapped.getCity(), putClubRequest.getCity());
        assertEquals(mapped.getName(), putClubRequest.getName());
    }
}