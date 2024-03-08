package wojtanowski.konrad.playerapp.club.model.model;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetClubsResponse {
    private List<GetClubResponse> clubs;
}
