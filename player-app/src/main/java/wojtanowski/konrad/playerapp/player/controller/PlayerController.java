package wojtanowski.konrad.playerapp.player.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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

    private final PlayerService playerService;

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
}
