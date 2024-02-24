package com.wojtanowski.konrad.clubapp.initializer;

import com.wojtanowski.konrad.clubapp.club.model.entity.Club;
import com.wojtanowski.konrad.clubapp.club.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInitializer implements InitializingBean {
    private final ClubRepository clubRepository;

    @Override
    public void afterPropertiesSet() {
        Club lublin = Club.builder()
                .id(UUID.fromString("77588f1a-c0fd-4b9c-b5d5-04b5777e9e98"))
                .city("Lublin")
                .name("Motor")
                .build();
        clubRepository.save(lublin);

        Club wroclaw = Club.builder()
                .id(UUID.fromString("12484856-2f73-4cda-a064-0651b749a6cb"))
                .city("Wrocław")
                .name("Sparta")
                .build();
        clubRepository.save(wroclaw);

        Club torun = Club.builder()
                .id(UUID.fromString("52201804-7794-4589-96e1-dfb9353039c8"))
                .city("Toruń")
                .name("Apator")
                .build();
        clubRepository.save(torun);

        Club czestochowa = Club.builder()
                .id(UUID.fromString("a45f15cb-f5e0-4639-acff-f5a9a02bc820"))
                .city("Częstochowa")
                .name("Włókniarz")
                .build();
        clubRepository.save(czestochowa);

        Club gorzow = Club.builder()
                .id(UUID.fromString("2fd6e7c9-56e3-4bbf-8014-27d5f0168a7c"))
                .city("Gorzów")
                .name("Stal")
                .build();
        clubRepository.save(gorzow);

        Club leszno = Club.builder()
                .id(UUID.fromString("c5b4a96a-7489-4705-ac24-0c05079fc654"))
                .city("Leszno")
                .name("Unia")
                .build();
        clubRepository.save(leszno);

        Club grudziadz = Club.builder()
                .id(UUID.fromString("3652af68-ec3a-456c-b30d-76b77cfa78d5"))
                .city("Grzudziadz")
                .name("Gkm")
                .build();
        clubRepository.save(grudziadz);

        Club zielonaGora = Club.builder()
                .id(UUID.fromString("a9a7b0e5-d0b3-4da1-bd53-0fd811abd045"))
                .city("Zielona Góra")
                .name("Falubaz")
                .build();
        clubRepository.save(zielonaGora);
    }
}
