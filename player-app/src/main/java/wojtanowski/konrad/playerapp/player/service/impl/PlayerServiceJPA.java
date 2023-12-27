package wojtanowski.konrad.playerapp.player.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import wojtanowski.konrad.playerapp.player.mapper.PlayerMapper;
import wojtanowski.konrad.playerapp.player.model.dto.GetPlayerResponse;
import wojtanowski.konrad.playerapp.player.model.dto.GetPlayersResponse;
import wojtanowski.konrad.playerapp.player.model.dto.PostPlayerRequest;
import wojtanowski.konrad.playerapp.player.model.entity.Player;
import wojtanowski.konrad.playerapp.player.repository.PlayerRepository;
import wojtanowski.konrad.playerapp.player.service.api.PlayerService;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Primary
@Service
@Validated
@RequiredArgsConstructor
public class PlayerServiceJPA implements PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    @Override
    public GetPlayersResponse getAllPlayers() {
        return GetPlayersResponse.builder()
                .players(playerRepository.findAll().stream()
                        .map(playerMapper::playerToGetPlayerResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public Optional<GetPlayerResponse> getPlayerById(UUID playerId) {
        Optional<Player> found = playerRepository.findById(playerId);

        return found.map(playerMapper::playerToGetPlayerResponse);
    }

    @Override
    public GetPlayerResponse saveNewPlayer(PostPlayerRequest player) {
        return playerMapper.playerToGetPlayerResponse(playerRepository.save(playerMapper.postPlayerRequestToPlayer(player)));
    }
}
