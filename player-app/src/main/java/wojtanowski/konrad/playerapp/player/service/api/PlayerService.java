package wojtanowski.konrad.playerapp.player.service.api;

import wojtanowski.konrad.playerapp.player.model.dto.GetPlayerResponse;
import wojtanowski.konrad.playerapp.player.model.dto.GetPlayersResponse;

import java.util.Optional;
import java.util.UUID;

public interface PlayerService {
    GetPlayersResponse getAllPlayers();

    Optional<GetPlayerResponse> getPlayerById(UUID playerId);
}
