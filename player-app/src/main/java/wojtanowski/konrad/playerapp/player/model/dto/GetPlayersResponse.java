package wojtanowski.konrad.playerapp.player.model.dto;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetPlayersResponse {
    private List<GetPlayerResponse> players;
}
