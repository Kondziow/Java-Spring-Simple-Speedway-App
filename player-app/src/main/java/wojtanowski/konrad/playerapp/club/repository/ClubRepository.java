package wojtanowski.konrad.playerapp.club.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wojtanowski.konrad.playerapp.club.model.entity.Club;

import java.util.UUID;

@Repository
public interface ClubRepository extends JpaRepository<Club, UUID> {
}
