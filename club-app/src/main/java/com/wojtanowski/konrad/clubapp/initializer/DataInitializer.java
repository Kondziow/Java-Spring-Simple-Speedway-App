package com.wojtanowski.konrad.clubapp.initializer;

import com.wojtanowski.konrad.clubapp.club.model.entity.Club;
import com.wojtanowski.konrad.clubapp.club.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements InitializingBean {
    private final ClubRepository clubRepository;

    @Override
    public void afterPropertiesSet() {
        Club lublin = Club.builder()
                .city("Lublin")
                .name("Motor")
                .build();
        clubRepository.save(lublin);

        Club wroclaw = Club.builder()
                .city("Wrocław")
                .name("Sparta")
                .build();
        clubRepository.save(wroclaw);

        Club torun = Club.builder()
                .city("Toruń")
                .name("Apator")
                .build();
        clubRepository.save(torun);

        Club czestochowa = Club.builder()
                .city("Częstochowa")
                .name("Włókniarz")
                .build();
        clubRepository.save(czestochowa);

        Club gorzow = Club.builder()
                .city("Gorzów")
                .name("Stal")
                .build();
        clubRepository.save(gorzow);

        Club leszno = Club.builder()
                .city("Leszno")
                .name("Unia")
                .build();
        clubRepository.save(leszno);

        Club grudziadz = Club.builder()
                .city("Grzudziadz")
                .name("Gkm")
                .build();
        clubRepository.save(grudziadz);

        Club zielonaGora = Club.builder()
                .city("Zielona Góra")
                .name("Falubaz")
                .build();
        clubRepository.save(zielonaGora);
    }
}
