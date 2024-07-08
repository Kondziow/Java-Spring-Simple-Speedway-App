package com.wojtanowski.konrad.clubapp.club.service.impl;

import com.wojtanowski.konrad.clubapp.club.event.api.ClubEventRepository;
import com.wojtanowski.konrad.clubapp.club.mapper.ClubMapper;
import com.wojtanowski.konrad.clubapp.club.model.dto.GetClubResponse;
import com.wojtanowski.konrad.clubapp.club.model.dto.GetClubsResponse;
import com.wojtanowski.konrad.clubapp.club.model.dto.PostClubRequest;
import com.wojtanowski.konrad.clubapp.club.model.dto.PutClubRequest;
import com.wojtanowski.konrad.clubapp.club.model.entity.Club;
import com.wojtanowski.konrad.clubapp.club.repository.ClubRepository;
import com.wojtanowski.konrad.clubapp.club.service.api.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
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
    private final ClubEventRepository clubEventRepository;

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
        GetClubResponse saved = clubMapper.clubToGetClubResponse(clubRepository.save(clubMapper.postClubRequestToClub(club)));

        clubEventRepository.create(saved.getId());

        return saved;
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
            clubEventRepository.delete(clubId);
            return true;
        }
        return false;
    }

    @Override
    public ByteArrayResource getClubImage(UUID clubId) {
        Optional<Club> clubOptional = clubRepository.findById(clubId);

        if (clubOptional.isPresent()) {
            Club club = clubOptional.get();
            byte[] image = club.getImage();
            return new ByteArrayResource(image);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Club not found");
        }
    }

    @Override
    public ByteArrayResource saveClubImage(UUID clubId, MultipartFile file) {
        Optional<Club> clubOptional = clubRepository.findById(clubId);

        if (clubOptional.isPresent()) {
            Club club = clubOptional.get();
            try {
                club.setImage(file.getBytes());
                Club newClub = clubRepository.save(club);
                return new ByteArrayResource(newClub.getImage());
            } catch (IOException e) {
                throw new RuntimeException("Failed to store image file", e);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Club not found");
        }
    }
}
