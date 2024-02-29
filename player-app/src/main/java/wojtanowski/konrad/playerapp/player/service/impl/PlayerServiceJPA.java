package wojtanowski.konrad.playerapp.player.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import wojtanowski.konrad.playerapp.player.mapper.PlayerMapper;
import wojtanowski.konrad.playerapp.player.model.dto.GetPlayerResponse;
import wojtanowski.konrad.playerapp.player.model.dto.GetPlayersResponse;
import wojtanowski.konrad.playerapp.player.model.dto.PostPlayerRequest;
import wojtanowski.konrad.playerapp.player.model.dto.PutPlayerRequest;
import wojtanowski.konrad.playerapp.player.model.entity.Player;
import wojtanowski.konrad.playerapp.player.repository.PlayerRepository;
import wojtanowski.konrad.playerapp.player.service.api.PlayerService;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Primary
@Service
@Validated
@RequiredArgsConstructor
public class PlayerServiceJPA implements PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    @Override
    public GetPlayersResponse getPlayersByClubId(UUID clubId) {
        return GetPlayersResponse.builder()
                .players(playerRepository.findByClub_Id(clubId).stream()
                        .map(playerMapper::playerToGetPlayerResponse)
                        .collect(Collectors.toList()))
                .build();
    }

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

    @Override
    public Optional<GetPlayerResponse> updatePlayerById(UUID playerId, PutPlayerRequest player) {
        AtomicReference<Optional<GetPlayerResponse>> atomicReference = new AtomicReference<>();

        playerRepository.findById(playerId).ifPresentOrElse(found -> {
            Player playerWithId = playerMapper.putPlayerRequestToPlayer(player);
            playerWithId.setId(playerId);
            atomicReference.set(Optional.of(playerMapper
                    .playerToGetPlayerResponse(playerRepository
                            .save(playerWithId))));
        }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    @Override
    public Boolean deletePlayerById(UUID playerId) {
        if (playerRepository.existsById(playerId)) {
            playerRepository.deleteById(playerId);
            return true;
        }
        return false;
    }
}
