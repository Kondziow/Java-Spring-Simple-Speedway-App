package wojtanowski.konrad.playerapp.club.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import wojtanowski.konrad.playerapp.club.model.model.GetClubResponse;
import wojtanowski.konrad.playerapp.club.model.model.GetClubsResponse;
import wojtanowski.konrad.playerapp.club.model.model.PostClubRequest;
import wojtanowski.konrad.playerapp.club.service.api.ClubService;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ClubController {
    public static final String CLUB_PATH = "/api/v1/clubs";
    public static final String CLUB_PATH_ID = CLUB_PATH + "/{clubId}";

    private final ClubService clubService;

    @PostMapping(CLUB_PATH_ID)
    public ResponseEntity<Void> postClub(@PathVariable("clubId") UUID clubId, PostClubRequest club) {
        club.setId(clubId);
        GetClubResponse getClubResponse = clubService.saveNewClub(club);
        return ResponseEntity.created(URI.create(CLUB_PATH + "/" + getClubResponse.getId())).build();
    }

    @DeleteMapping(CLUB_PATH_ID)
    public ResponseEntity<Void> deleteClubById(@PathVariable("clubId") UUID clubId) {
        if (!clubService.deleteClubById(clubId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/v1/players/clubs")
    public ResponseEntity<GetClubsResponse> getAllClubs() {
        return ResponseEntity.ok(clubService.getAllClubs());
    }
}
