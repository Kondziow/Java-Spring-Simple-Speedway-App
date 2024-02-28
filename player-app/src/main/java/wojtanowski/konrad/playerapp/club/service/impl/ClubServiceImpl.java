package wojtanowski.konrad.playerapp.club.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wojtanowski.konrad.playerapp.club.mapper.ClubMapper;
import wojtanowski.konrad.playerapp.club.model.entity.Club;
import wojtanowski.konrad.playerapp.club.model.model.GetClubResponse;
import wojtanowski.konrad.playerapp.club.model.model.PostClubRequest;
import wojtanowski.konrad.playerapp.club.repository.ClubRepository;
import wojtanowski.konrad.playerapp.club.service.api.ClubService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {
    private final ClubRepository clubRepository;
    private final ClubMapper clubMapper;

    @Override
    public GetClubResponse saveNewClub(PostClubRequest club) {
        Club saved = clubRepository.save(clubMapper.postClubRequestToClub(club));
        return clubMapper.clubToGetClubResponse(saved);
    }

    @Override
    public void deleteClubById(UUID clubId) {
        clubRepository.deleteById(clubId);
    }
}
