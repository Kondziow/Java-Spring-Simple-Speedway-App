package com.wojtanowski.konrad.clubapp.club.service;

import com.wojtanowski.konrad.clubapp.club.model.entity.Club;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClubService {
    List<Club> getAllClubs();

    Optional<Club> getClubById(UUID clubId);
}
