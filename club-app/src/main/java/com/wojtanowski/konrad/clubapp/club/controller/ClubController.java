package com.wojtanowski.konrad.clubapp.club.controller;

import com.wojtanowski.konrad.clubapp.club.mapper.ClubMapper;
import com.wojtanowski.konrad.clubapp.club.model.dto.GetClubResponse;
import com.wojtanowski.konrad.clubapp.club.model.dto.GetClubsResponse;
import com.wojtanowski.konrad.clubapp.club.model.entity.Club;
import com.wojtanowski.konrad.clubapp.club.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ClubController {
    public static final String CLUB_PATH = "/api/v1/clubs";
    public static final String CLUB_PATH_ID = CLUB_PATH + "/{clubId}";

    private final ClubService clubService;
    private final ClubMapper clubMapper;

    @GetMapping(CLUB_PATH)
    public ResponseEntity<GetClubsResponse> getAllClubs() {
        return ResponseEntity.ok(GetClubsResponse.builder()
                .clubs(clubService.getAllClubs().stream()
                        .map(clubMapper::clubToGetClubResponse)
                        .collect(Collectors.toList()))
                .build());
    }

    @GetMapping(CLUB_PATH_ID)
    public ResponseEntity<GetClubResponse> getClubById(@PathVariable("clubId")UUID clubId) {
        Optional<Club> foundClubOptional = clubService.getClubById(clubId);

        if (foundClubOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Club foundClub = foundClubOptional.get();

        GetClubResponse clubResponse = clubMapper.clubToGetClubResponse(foundClub);
        System.out.println("clubResponse: " + clubResponse); // Dodaj tę linię

        return new ResponseEntity<>(clubResponse, HttpStatus.OK);
    }
}
