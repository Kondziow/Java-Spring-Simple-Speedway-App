package wojtanowski.konrad.playerapp.club.model.model;

import lombok.*;
import wojtanowski.konrad.playerapp.player.model.entity.Player;

import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostClubRequest {
    private UUID id;
    private List<Player> players;
}
