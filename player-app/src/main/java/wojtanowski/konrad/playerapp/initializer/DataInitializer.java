package wojtanowski.konrad.playerapp.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import wojtanowski.konrad.playerapp.club.model.entity.Club;
import wojtanowski.konrad.playerapp.club.repository.ClubRepository;
import wojtanowski.konrad.playerapp.player.model.entity.Player;
import wojtanowski.konrad.playerapp.player.repository.PlayerRepository;

import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInitializer implements InitializingBean {
    private final PlayerRepository playerRepository;
    private final ClubRepository clubRepository;

    @Override
    public void afterPropertiesSet() {
        Club torun = Club.builder()
                .id(UUID.fromString("52201804-7794-4589-96e1-dfb9353039c8"))
                .build();
        clubRepository.save(torun);

        Club lublin = Club.builder()
                .id(UUID.fromString("77588f1a-c0fd-4b9c-b5d5-04b5777e9e98"))
                .build();
        clubRepository.save(lublin);

        Club wroclaw = Club.builder()
                .id(UUID.fromString("12484856-2f73-4cda-a064-0651b749a6cb"))
                .build();
        clubRepository.save(wroclaw);

        Club czestochowa = Club.builder()
                .id(UUID.fromString("a45f15cb-f5e0-4639-acff-f5a9a02bc820"))
                .build();
        clubRepository.save(czestochowa);

        Club gorzow = Club.builder()
                .id(UUID.fromString("2fd6e7c9-56e3-4bbf-8014-27d5f0168a7c"))
                .build();
        clubRepository.save(gorzow);

        Club leszno = Club.builder()
                .id(UUID.fromString("c5b4a96a-7489-4705-ac24-0c05079fc654"))
                .build();
        clubRepository.save(leszno);

        Club grudziadz = Club.builder()
                .id(UUID.fromString("3652af68-ec3a-456c-b30d-76b77cfa78d5"))
                .build();
        clubRepository.save(grudziadz);

        Club zielonaGora = Club.builder()
                .id(UUID.fromString("a9a7b0e5-d0b3-4da1-bd53-0fd811abd045"))
                .build();
        clubRepository.save(zielonaGora);


        Player emilSajfutdinow = Player.builder()
                .name("Emil")
                .surname("Sajfutdinow")
                .birthDate(LocalDate.of(1989,10, 26))
                .club(torun)
                .build();
        playerRepository.save(emilSajfutdinow);

        Player robertLambert = Player.builder()
                .name("Robert")
                .surname("Lambert")
                .birthDate(LocalDate.of(1998, 4, 5))
                .club(torun)
                .build();
        playerRepository.save(robertLambert);

        Player taiWoffinden = Player.builder()
                .name("Tai")
                .surname("Woffinden")
                .birthDate(LocalDate.of(1990, 8, 10))
                .club(wroclaw)
                .build();
        playerRepository.save(taiWoffinden);

        Player maciejJanowski = Player.builder()
                .name("<aciej")
                .surname("Janowski")
                .birthDate(LocalDate.of(1991, 8, 6))
                .club(wroclaw)
                .build();
        playerRepository.save(maciejJanowski);
    }
}
