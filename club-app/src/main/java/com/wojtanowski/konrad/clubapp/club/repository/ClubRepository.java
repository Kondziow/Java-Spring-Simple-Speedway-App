package com.wojtanowski.konrad.clubapp.club.repository;

import com.wojtanowski.konrad.clubapp.club.model.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClubRepository extends JpaRepository<Club, UUID> {
}
