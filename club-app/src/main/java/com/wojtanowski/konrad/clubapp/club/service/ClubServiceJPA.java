package com.wojtanowski.konrad.clubapp.club.service;

import com.wojtanowski.konrad.clubapp.club.mapper.ClubMapper;
import com.wojtanowski.konrad.clubapp.club.model.dto.GetClubResponse;
import com.wojtanowski.konrad.clubapp.club.model.dto.GetClubsResponse;
import com.wojtanowski.konrad.clubapp.club.model.dto.PostClubRequest;
import com.wojtanowski.konrad.clubapp.club.model.dto.PutClubRequest;
import com.wojtanowski.konrad.clubapp.club.model.entity.Club;
import com.wojtanowski.konrad.clubapp.club.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Primary
@Service
@Validated
@RequiredArgsConstructor
public class ClubServiceJPA implements ClubService {
    private final ClubRepository clubRepository;
    private final ClubMapper clubMapper;

    @Override
    public GetClubsResponse getAllClubs() {

        return GetClubsResponse.builder()
                .clubs(clubRepository.findAll().stream()
                        .map(clubMapper::clubToGetClubResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public Optional<GetClubResponse> getClubById(UUID clubId) {
        Optional<Club> found = clubRepository.findById(clubId);

        return found.map(clubMapper::clubToGetClubResponse);
    }

    @Override
    public GetClubResponse saveNewClub(PostClubRequest club) {
        return clubMapper.clubToGetClubResponse(clubRepository.save(clubMapper.postClubRequestToClub(club)));
    }

    @Override
    public Optional<GetClubResponse> updateClubById(UUID clubId, PutClubRequest club) {
        AtomicReference<Optional<GetClubResponse>> atomicReference = new AtomicReference<>();

        clubRepository.findById(clubId).ifPresentOrElse(found -> {
            Club clubWithId = clubMapper.putClubRequestToClub(club);
            clubWithId.setId(clubId);
            atomicReference.set(Optional.of(clubMapper
                    .clubToGetClubResponse(clubRepository
                            .save(clubWithId))));
        }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    @Override
    public Boolean deleteClubById(UUID clubId) {
        if (clubRepository.existsById(clubId)) {
            clubRepository.deleteById(clubId);
            return true;
        }
        return false;
    }
}
