package wojtanowski.konrad.playerapp.player.controller;

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
import wojtanowski.konrad.playerapp.club.controller.ClubController;
import wojtanowski.konrad.playerapp.player.model.dto.GetPlayerResponse;
import wojtanowski.konrad.playerapp.player.model.dto.GetPlayersResponse;
import wojtanowski.konrad.playerapp.player.model.dto.PostPlayerRequest;
import wojtanowski.konrad.playerapp.player.model.dto.PutPlayerRequest;
import wojtanowski.konrad.playerapp.player.service.api.PlayerService;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PlayerController {
    public static final String PLAYER_PATH = "/api/v1/players";
    public static final String PLAYER_PATH_ID = PLAYER_PATH + "/{playerId}";
    public static final String CLUB_PATH_ID_PLAYERS = ClubController.CLUB_PATH_ID + "/players";
    public static final String PLAYER_IMAGE_PATH = PLAYER_PATH_ID + "/image";

    private final PlayerService playerService;

    @GetMapping(CLUB_PATH_ID_PLAYERS)
    public ResponseEntity<GetPlayersResponse> getPlayersByClubId(@PathVariable("clubId") UUID clubId) {
        return ResponseEntity.ok(playerService.getPlayersByClubId(clubId));
    }

    @GetMapping(PLAYER_PATH)
    public ResponseEntity<GetPlayersResponse> getAllPlayers() {
        return ResponseEntity.ok(playerService.getAllPlayers());
    }

    @GetMapping(PLAYER_PATH_ID)
    public ResponseEntity<GetPlayerResponse> getPlayerById(@PathVariable("playerId") UUID playerId) {
        Optional<GetPlayerResponse> foundPlayerOptional = playerService.getPlayerById(playerId);

        if (foundPlayerOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        GetPlayerResponse playerResponse = foundPlayerOptional.get();

        return new ResponseEntity<>(playerResponse, HttpStatus.OK);
    }

    @PostMapping(PLAYER_PATH)
    public ResponseEntity<GetPlayerResponse> postPlayer(@Validated @RequestBody PostPlayerRequest player) {
        GetPlayerResponse savedPlayer = playerService.saveNewPlayer(player);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", PLAYER_PATH + "/" + savedPlayer.getId().toString());

        return new ResponseEntity<>(savedPlayer, headers, HttpStatus.CREATED);
    }

    @PutMapping(PLAYER_PATH_ID)
    public ResponseEntity<GetPlayerResponse> putPlayerById(
            @PathVariable("playerId") UUID playerId,
            @Validated @RequestBody PutPlayerRequest player
    ){
        Optional<GetPlayerResponse> getPlayerResponse = playerService.updatePlayerById(playerId, player);
        if (getPlayerResponse.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        GetPlayerResponse response = getPlayerResponse.get();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(PLAYER_PATH_ID)
    public ResponseEntity<Void> deletePlayerById(@PathVariable("playerId") UUID playerId) {
        if (!playerService.deletePlayerById(playerId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = PLAYER_IMAGE_PATH, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<ByteArrayResource> getPlayerImage(@PathVariable("playerId") UUID playerId) {
        ByteArrayResource image = playerService.getPlayerImage(playerId);

        if (image == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @PostMapping(PLAYER_IMAGE_PATH)
    public ResponseEntity<ByteArrayResource> uploadPlayerImage(
            @PathVariable("playerId") UUID playerId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            ByteArrayResource image = playerService.savePlayerImage(playerId, file);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to upload image", e);
        }
    }
}
