package wojtanowski.konrad.playerapp.player.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wojtanowski.konrad.playerapp.player.model.dto.GetPlayersResponse;
import wojtanowski.konrad.playerapp.player.service.api.PlayerService;

@RestController
@RequiredArgsConstructor
public class PlayerController {
    public static final String PLAYER_PATH = "/api/v1/players";

    private final PlayerService playerService;

    @GetMapping(PLAYER_PATH)
    public ResponseEntity<GetPlayersResponse> getAllPlayers(){
        return ResponseEntity.ok(playerService.getAllPlayers());
    }
}
