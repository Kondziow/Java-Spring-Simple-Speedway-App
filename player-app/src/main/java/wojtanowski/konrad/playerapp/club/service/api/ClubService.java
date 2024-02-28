package wojtanowski.konrad.playerapp.club.service.api;

import wojtanowski.konrad.playerapp.club.model.model.GetClubResponse;
import wojtanowski.konrad.playerapp.club.model.model.PostClubRequest;

import java.util.UUID;

public interface ClubService {
    GetClubResponse saveNewClub(PostClubRequest club);

    void deleteClubById(UUID clubId);
}
