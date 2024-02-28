package wojtanowski.konrad.playerapp.club.mapper;

import org.mapstruct.Mapper;
import wojtanowski.konrad.playerapp.club.model.entity.Club;
import wojtanowski.konrad.playerapp.club.model.model.GetClubResponse;
import wojtanowski.konrad.playerapp.club.model.model.PostClubRequest;

@Mapper
public interface ClubMapper {
    Club postClubRequestToClub(PostClubRequest putClubRequest);
    GetClubResponse clubToGetClubResponse(Club club);
}
