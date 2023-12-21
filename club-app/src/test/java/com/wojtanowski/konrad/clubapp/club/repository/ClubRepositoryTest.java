package com.wojtanowski.konrad.clubapp.club.repository;

import com.wojtanowski.konrad.clubapp.club.model.entity.Club;
import com.wojtanowski.konrad.clubapp.initializer.DataInitializer;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Import(DataInitializer.class)
class ClubRepositoryTest {
    @Autowired
    ClubRepository clubRepository;

    @Test
    void getAllClubs() {
        List<Club> clubs = clubRepository.findAll();

        assertThat(clubs).isNotNull();
        assertThat(clubs.size()).isEqualTo(8);
    }

    @Test
    void testGetClubById() {
        Club club = Club.builder()
                .name("n")
                .city("c")
                .build();

        Club saved = clubRepository.save(club);
        clubRepository.flush();

        Club found = clubRepository.findById(saved.getId()).get();

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(saved.getId());
        assertThat(found.getName()).isEqualTo(saved.getName());
        assertThat(found.getCity()).isEqualTo(saved.getCity());
    }

    @Test
    void testSaveClub() {
        Club club = Club.builder()
                .name("n")
                .city("c")
                .build();

        Club saved = clubRepository.save(club);
        clubRepository.flush();

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getId().toString()).isGreaterThan("0");
        assertThat(saved.getName()).isEqualTo("n");
        assertThat(saved.getCity()).isEqualTo("c");
    }

    @Test
    void testSaveClubNullName() {
        assertThrows(ConstraintViolationException.class, () -> {
            clubRepository.save(Club.builder().city("c").build());
            clubRepository.flush();
        });
    }

    @Test
    void testSaveClubNullCity() {
        assertThrows(ConstraintViolationException.class, () -> {
            clubRepository.save(Club.builder().name("n").build());
            clubRepository.flush();
        });
    }

    @Test
    void testSaveClubNameTooLong() {
        assertThrows(ConstraintViolationException.class, () -> {
            clubRepository.save(Club.builder()
                    .city("c")
                    .name("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn")
                    .build());
            clubRepository.flush();
        });
    }

    @Test
    void testSaveClubCityTooLong() {
        assertThrows(ConstraintViolationException.class, () -> {
            clubRepository.save(Club.builder()
                    .city("cccccccccccccccccccccccccccccccccccccccc")
                    .name("n")
                    .build());
            clubRepository.flush();
        });
    }

    @Test
    void testUpdateClub() {
        Club club = Club.builder()
                .name("n")
                .city("c")
                .build();

        Club saved = clubRepository.save(club);
        clubRepository.flush();

        saved.setName("UPDATED");

        Club updated = clubRepository.save(saved);

        assertThat(saved.getId()).isEqualTo(updated.getId());
        assertThat(saved.getName()).isEqualTo(updated.getName());
        assertThat(saved.getName()).isEqualTo("UPDATED");
        assertThat(saved.getCity()).isEqualTo(updated.getCity());
        assertThat(saved.getCity()).isEqualTo("c");
    }

    @Test
    void testDeleteClubById() {
        Club club = Club.builder()
                .name("n")
                .city("c")
                .build();

        Club saved = clubRepository.save(club);
        clubRepository.flush();

        assertThat(saved).isNotNull();

        clubRepository.deleteById(saved.getId());

        assertThat(clubRepository.findById(saved.getId())).isEmpty();
    }
}





