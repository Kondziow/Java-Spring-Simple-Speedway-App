package wojtanowski.konrad.playerapp.club.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import wojtanowski.konrad.playerapp.club.mapper.ClubMapper;
import wojtanowski.konrad.playerapp.club.model.entity.Club;
import wojtanowski.konrad.playerapp.club.model.model.GetClubResponse;
import wojtanowski.konrad.playerapp.club.model.model.GetClubsResponse;
import wojtanowski.konrad.playerapp.club.model.model.PostClubRequest;
import wojtanowski.konrad.playerapp.club.repository.ClubRepository;
import wojtanowski.konrad.playerapp.club.service.api.ClubService;

import java.util.UUID;
import java.util.stream.Collectors;

@Primary
@Service
@RequiredArgsConstructor
public class ClubServiceJPA implements ClubService {
    private final ClubRepository clubRepository;
    private final ClubMapper clubMapper;

    @Override
    public GetClubResponse saveNewClub(PostClubRequest club) {
        Club saved = clubRepository.save(clubMapper.postClubRequestToClub(club));
        return clubMapper.clubToGetClubResponse(saved);
    }

    @Override
    public Boolean deleteClubById(UUID clubId) {
        if (clubRepository.existsById(clubId)) {
            clubRepository.deleteById(clubId);
            return true;
        }
        return false;
    }

    @Override
    public GetClubsResponse getAllClubs() {
        return GetClubsResponse.builder()
                .clubs(clubRepository.findAll().stream()
                        .map(clubMapper::clubToGetClubResponse)
                        .collect(Collectors.toList()))
                .build();
    }
}
