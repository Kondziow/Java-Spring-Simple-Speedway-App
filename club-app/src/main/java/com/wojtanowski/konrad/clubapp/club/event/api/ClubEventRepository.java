package com.wojtanowski.konrad.clubapp.club.event.api;

import java.util.UUID;

public interface ClubEventRepository {
    void create(UUID uuid);
    void delete(UUID uuid);
}
