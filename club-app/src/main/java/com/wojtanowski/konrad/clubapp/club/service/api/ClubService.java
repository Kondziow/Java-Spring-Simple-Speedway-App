package com.wojtanowski.konrad.clubapp.club.service.api;

import com.wojtanowski.konrad.clubapp.club.model.dto.GetClubResponse;
import com.wojtanowski.konrad.clubapp.club.model.dto.GetClubsResponse;
import com.wojtanowski.konrad.clubapp.club.model.dto.PostClubRequest;
import com.wojtanowski.konrad.clubapp.club.model.dto.PutClubRequest;
import jakarta.validation.Valid;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

public interface ClubService {
    GetClubsResponse getAllClubs();

    Optional<GetClubResponse> getClubById(UUID clubId);

    GetClubResponse saveNewClub(@Valid PostClubRequest club);

    Optional<GetClubResponse> updateClubById(UUID clubId, @Valid PutClubRequest club);

    Boolean deleteClubById(UUID clubId);

    ByteArrayResource getClubImage(UUID clubId);

    ByteArrayResource saveClubImage(UUID clubId, MultipartFile file);
}
