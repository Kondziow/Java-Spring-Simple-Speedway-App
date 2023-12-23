package com.wojtanowski.konrad.clubapp.club.controller;

import com.wojtanowski.konrad.clubapp.club.model.dto.GetClubResponse;
import com.wojtanowski.konrad.clubapp.club.model.dto.GetClubsResponse;
import com.wojtanowski.konrad.clubapp.club.model.dto.PostClubRequest;
import com.wojtanowski.konrad.clubapp.club.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ClubController {
    public static final String CLUB_PATH = "/api/v1/clubs";
    public static final String CLUB_PATH_ID = CLUB_PATH + "/{clubId}";

    private final ClubService clubService;

    @GetMapping(CLUB_PATH)
    public ResponseEntity<GetClubsResponse> getAllClubs() {
        return ResponseEntity.ok(clubService.getAllClubs());
    }

    @GetMapping(CLUB_PATH_ID)
    public ResponseEntity<GetClubResponse> getClubById(@PathVariable("clubId")UUID clubId) {
        Optional<GetClubResponse> foundClubOptional = clubService.getClubById(clubId);

        if (foundClubOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        GetClubResponse clubResponse = foundClubOptional.get();

        return new ResponseEntity<>(clubResponse, HttpStatus.OK);
    }

    @PostMapping(CLUB_PATH)
    public ResponseEntity<GetClubResponse> postClub(@Validated @RequestBody PostClubRequest club) {
        GetClubResponse savedClub = clubService.saveNewClub(club);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", CLUB_PATH + "/" + savedClub.getId().toString());

        return new ResponseEntity<>(savedClub , headers, HttpStatus.CREATED);
    }
}
