package wojtanowski.konrad.playerapp.player.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import wojtanowski.konrad.playerapp.player.mapper.PlayerMapper;
import wojtanowski.konrad.playerapp.player.model.dto.GetPlayersResponse;
import wojtanowski.konrad.playerapp.player.repository.PlayerRepository;
import wojtanowski.konrad.playerapp.player.service.api.PlayerService;

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
}
