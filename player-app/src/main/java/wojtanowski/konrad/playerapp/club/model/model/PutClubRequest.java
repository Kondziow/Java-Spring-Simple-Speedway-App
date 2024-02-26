package wojtanowski.konrad.playerapp.club.model.model;

import lombok.*;
import wojtanowski.konrad.playerapp.player.model.entity.Player;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PutClubRequest {
    private List<Player> players;
}
