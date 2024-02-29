package wojtanowski.konrad.playerapp.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wojtanowski.konrad.playerapp.player.model.entity.Player;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, UUID> {
    List<Player> findByClub_Id(UUID clubId);
}
