package wojtanowski.konrad.playerapp.player.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import wojtanowski.konrad.playerapp.player.model.dto.GetPlayerResponse;
import wojtanowski.konrad.playerapp.player.model.dto.GetPlayersResponse;
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
}
