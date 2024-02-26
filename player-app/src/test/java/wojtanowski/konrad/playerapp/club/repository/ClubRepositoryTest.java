package wojtanowski.konrad.playerapp.club.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import wojtanowski.konrad.playerapp.club.model.entity.Club;
import wojtanowski.konrad.playerapp.initializer.DataInitializer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Import(DataInitializer.class)
class ClubRepositoryTest {
    @Autowired
    ClubRepository clubRepository;

    @Test
    void testGetAllClubs() {
        List<Club> clubs = clubRepository.findAll();

        assertThat(clubs).isNotNull();
        assertThat(clubs.size()).isEqualTo(8);
    }

    @Test
    void testGetClubById() {
        Club club = Club.builder().build();

        Club saved = clubRepository.save(club);
        clubRepository.flush();

        Club found = clubRepository.findById(saved.getId()).get();

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(saved.getId());
    }

    @Test
    void testSaveClub() {
        Club club = Club.builder().build();

        Club saved = clubRepository.save(club);
        clubRepository.flush();

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isEqualTo(club.getId());
    }

    @Test
    void testDeleteClubById() {
        Club club = Club.builder().build();

        Club saved = clubRepository.save(club);
        clubRepository.flush();

        assertThat(saved).isNotNull();

        clubRepository.deleteById(saved.getId());

        assertThat(clubRepository.findById(saved.getId())).isEmpty();
    }
}