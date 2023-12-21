package com.wojtanowski.konrad.clubapp.club.service;

import com.wojtanowski.konrad.clubapp.club.model.entity.Club;
import com.wojtanowski.konrad.clubapp.club.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Primary
@Service
@Validated
@RequiredArgsConstructor
public class ClubServiceJPA implements ClubService {
    @Autowired
    ClubRepository clubRepository;

    @Override
    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }
}
