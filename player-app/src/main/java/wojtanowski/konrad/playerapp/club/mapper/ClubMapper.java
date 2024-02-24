package wojtanowski.konrad.playerapp.club.mapper;

import org.mapstruct.Mapper;
import wojtanowski.konrad.playerapp.club.model.entity.Club;
import wojtanowski.konrad.playerapp.club.model.model.PutClubRequest;

@Mapper
public interface ClubMapper {
    Club putClubRequestToClub(PutClubRequest putClubRequest);
}
