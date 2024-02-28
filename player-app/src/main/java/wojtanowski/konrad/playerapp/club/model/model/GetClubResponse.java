package wojtanowski.konrad.playerapp.club.model.model;

import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetClubResponse {
    private UUID id;
}
