package wojtanowski.konrad.playerapp.player.repository;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import wojtanowski.konrad.playerapp.initializer.DataInitializer;
import wojtanowski.konrad.playerapp.player.model.entity.Player;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Import(DataInitializer.class)
class PlayerRepositoryTest {
    @Autowired
    PlayerRepository playerRepository;

    @Test
    void testGetAllPlayers() {
        List<Player> players = playerRepository.findAll();

        assertThat(players).isNotNull();
        assertThat(players.size()).isEqualTo(4);
    }

    @Test
    void testGetPlayerById() {
        Player player = Player.builder()
                .name("n")
                .surname("s")
                .birthDate(LocalDate.of(2000, 10, 10))
                .build();

        Player saved = playerRepository.save(player);
        playerRepository.flush();

        Player found = playerRepository.findById(saved.getId()).get();

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(saved.getId());
        assertThat(found.getName()).isEqualTo(saved.getName());
        assertThat(found.getSurname()).isEqualTo(saved.getSurname());
        assertThat(found.getBirthDate()).isEqualTo(saved.getBirthDate());
    }

    @Test
    void testSavePlayer() {
        Player player = Player.builder()
                .name("n")
                .surname("s")
                .birthDate(LocalDate.of(2000, 10, 10))
                .build();

        Player saved = playerRepository.save(player);
        playerRepository.flush();

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isEqualTo(player.getId());
        assertThat(saved.getName()).isEqualTo(player.getName());
        assertThat(saved.getSurname()).isEqualTo(player.getSurname());
        assertThat(saved.getBirthDate()).isEqualTo(player.getBirthDate());
    }

    @Test
    void testSavePlayerNullName() {
        assertThrows(ConstraintViolationException.class, () -> {
            playerRepository.save(Player.builder().surname("s").build());
            playerRepository.flush();
        });
    }

    @Test
    void testSavePlayerNullSurname() {
        assertThrows(ConstraintViolationException.class, () -> {
            playerRepository.save(Player.builder().name("n").build());
            playerRepository.flush();
        });
    }

    @Test
    void testSavePlayerTooLongName() {
        assertThrows(ConstraintViolationException.class, () -> {
            playerRepository.save(Player.builder()
                    .name("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn")
                    .surname("s")
                    .build());
            playerRepository.flush();
        });
    }

    @Test
    void testSavePlayerTooLongSurname() {
        assertThrows(ConstraintViolationException.class, () -> {
            playerRepository.save(Player.builder()
                    .name("n")
                    .surname("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss")
                    .build());
            playerRepository.flush();
        });
    }

    @Test
    void testUpdatePlayer() {
        Player player = Player.builder()
                .name("n")
                .surname("s")
                .birthDate(LocalDate.of(2000, 10, 10))
                .build();

        Player saved = playerRepository.save(player);
        playerRepository.flush();

        saved.setName("UPDATED");

        Player updated = playerRepository.save(saved);
        playerRepository.flush();

        assertThat(updated.getId()).isEqualTo(player.getId());
        assertThat(updated.getName()).isEqualTo("UPDATED");
        assertThat(updated.getSurname()).isEqualTo(player.getSurname());
        assertThat(updated.getBirthDate()).isEqualTo(player.getBirthDate());
    }

    @Test
    void testDeletePlayerById() {
        Player player = Player.builder()
                .name("n")
                .surname("s")
                .birthDate(LocalDate.of(2000, 10, 10))
                .build();

        Player saved = playerRepository.save(player);
        playerRepository.flush();

        assertThat(saved).isNotNull();

        playerRepository.deleteById(saved.getId());

        assertThat(playerRepository.findById(saved.getId())).isEmpty();
    }
}