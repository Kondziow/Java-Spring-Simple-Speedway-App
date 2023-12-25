package wojtanowski.konrad.playerapp.player.mapper;

import org.mapstruct.Mapper;
import wojtanowski.konrad.playerapp.player.model.dto.GetPlayerResponse;
import wojtanowski.konrad.playerapp.player.model.dto.PostPlayerRequest;
import wojtanowski.konrad.playerapp.player.model.dto.PutPlayerRequest;
import wojtanowski.konrad.playerapp.player.model.entity.Player;

@Mapper
public interface PlayerMapper {
    GetPlayerResponse playerToGetPlayerResponse(Player player);

    Player putPlayerRequestToPlayer(PutPlayerRequest putPlayerRequest);

    Player postPlayerRequestToPlayer(PostPlayerRequest putPlayerRequest);
}
