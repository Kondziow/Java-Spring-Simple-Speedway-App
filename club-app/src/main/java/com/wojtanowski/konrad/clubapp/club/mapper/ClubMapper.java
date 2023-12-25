package com.wojtanowski.konrad.clubapp.club.mapper;

import com.wojtanowski.konrad.clubapp.club.model.dto.GetClubResponse;
import com.wojtanowski.konrad.clubapp.club.model.dto.PostClubRequest;
import com.wojtanowski.konrad.clubapp.club.model.dto.PutClubRequest;
import com.wojtanowski.konrad.clubapp.club.model.entity.Club;
import org.mapstruct.Mapper;

@Mapper
public interface ClubMapper {
    GetClubResponse clubToGetClubResponse(Club club);

    Club putClubRequestToClub(PutClubRequest putClubRequest);

    Club postClubRequestToClub(PostClubRequest postClubRequest);
}
