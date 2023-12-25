package wojtanowski.konrad.playerapp.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import wojtanowski.konrad.playerapp.player.model.entity.Player;
import wojtanowski.konrad.playerapp.player.repository.PlayerRepository;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements InitializingBean {
    private final PlayerRepository playerRepository;

    @Override
    public void afterPropertiesSet() {
        Player emilSajfutdinow = Player.builder()
                .name("Emil")
                .surname("Sajfutdinow")
                .birthDate(LocalDate.of(1989,10, 26))
                .build();
        playerRepository.save(emilSajfutdinow);

        Player robertLambert = Player.builder()
                .name("Robert")
                .surname("Lambert")
                .birthDate(LocalDate.of(1998, 4, 5))
                .build();
        playerRepository.save(robertLambert);

        Player taiWoffinden = Player.builder()
                .name("Tai")
                .surname("Woffinden")
                .birthDate(LocalDate.of(1990, 8, 10))
                .build();
        playerRepository.save(taiWoffinden);

        Player maciejJanowski = Player.builder()
                .name("<aciej")
                .surname("Janowski")
                .birthDate(LocalDate.of(1991, 8, 6))
                .build();
        playerRepository.save(maciejJanowski);
    }
}
