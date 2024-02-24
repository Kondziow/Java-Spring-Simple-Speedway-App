package wojtanowski.konrad.playerapp.club.model.model;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PutClubRequest {
    private List<UUID> players;
}
