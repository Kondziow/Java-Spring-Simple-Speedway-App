package com.wojtanowski.konrad.clubapp.club.controller;

import com.wojtanowski.konrad.clubapp.club.model.dto.GetClubResponse;
import com.wojtanowski.konrad.clubapp.club.model.dto.GetClubsResponse;
import com.wojtanowski.konrad.clubapp.club.model.dto.PostClubRequest;
import com.wojtanowski.konrad.clubapp.club.model.dto.PutClubRequest;
import com.wojtanowski.konrad.clubapp.club.service.api.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ClubController {
    public static final String CLUB_PATH = "/api/v1/clubs";
    public static final String CLUB_PATH_ID = CLUB_PATH + "/{clubId}";
    public static final String CLUB_IMAGE_PATH = CLUB_PATH_ID + "/image";

    private final ClubService clubService;

    @GetMapping(CLUB_PATH)
    public ResponseEntity<GetClubsResponse> getAllClubs() {
        return ResponseEntity.ok(clubService.getAllClubs());
    }

    @GetMapping(CLUB_PATH_ID)
    public ResponseEntity<GetClubResponse> getClubById(@PathVariable("clubId") UUID clubId) {
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

        return new ResponseEntity<>(savedClub, headers, HttpStatus.CREATED);
    }

    @PutMapping(CLUB_PATH_ID)
    public ResponseEntity<GetClubResponse> putClubById(
            @PathVariable("clubId") UUID clubId,
            @Validated @RequestBody PutClubRequest club
    ) {
        Optional<GetClubResponse> getClubResponse = clubService.updateClubById(clubId, club);
        if (getClubResponse.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        GetClubResponse response = getClubResponse.get();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(CLUB_PATH_ID)
    public ResponseEntity<Void> deleteClubById(@PathVariable("clubId") UUID clubId) {
        if (!clubService.deleteClubById(clubId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = CLUB_IMAGE_PATH, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<ByteArrayResource> getClubImage(@PathVariable("clubId") UUID clubId) {
        ByteArrayResource image = clubService.getClubImage(clubId);

        if (image == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @PostMapping(CLUB_IMAGE_PATH)
    public ResponseEntity<ByteArrayResource> uploadClubImage(
            @PathVariable("clubId") UUID clubId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            ByteArrayResource image = clubService.saveClubImage(clubId, file);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to upload image", e);
        }
    }
}
