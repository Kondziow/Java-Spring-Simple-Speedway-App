package com.wojtanowski.konrad.clubapp.club.event.impl;

import com.wojtanowski.konrad.clubapp.club.event.api.ClubEventRepository;
import com.wojtanowski.konrad.clubapp.club.model.entity.Club;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Repository
public class ClubEventRepositoryImpl implements ClubEventRepository {
    private final RestTemplate restTemplate;

    @Autowired
    public ClubEventRepositoryImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void create(UUID uuid) {
        restTemplate.postForLocation("/api/v1/clubs/{clubId}", Club.builder().build(), uuid);
    }

    @Override
    public void delete(UUID uuid) {
        restTemplate.delete("/api/v1/clubs/{clubId}", uuid);
    }
}
