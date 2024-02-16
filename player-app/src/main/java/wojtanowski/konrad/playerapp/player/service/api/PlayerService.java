package wojtanowski.konrad.playerapp.player.service.api;

import jakarta.validation.Valid;
import wojtanowski.konrad.playerapp.player.model.dto.GetPlayerResponse;
import wojtanowski.konrad.playerapp.player.model.dto.GetPlayersResponse;
import wojtanowski.konrad.playerapp.player.model.dto.PostPlayerRequest;
import wojtanowski.konrad.playerapp.player.model.dto.PutPlayerRequest;

import java.util.Optional;
import java.util.UUID;

public interface PlayerService {
    GetPlayersResponse getAllPlayers();

    Optional<GetPlayerResponse> getPlayerById(UUID playerId);

    GetPlayerResponse saveNewPlayer(@Valid PostPlayerRequest player);

    Optional<GetPlayerResponse> updatePlayerById(UUID playerId, @Valid PutPlayerRequest player);

    Boolean deletePlayerById(UUID playerId);
}
