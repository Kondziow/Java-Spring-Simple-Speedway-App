package com.wojtanowski.konrad.clubapp.club.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@Getter
@Setter
public class Club {
    @Id
    @NotNull
    @Column(name = "club_id", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    @NotBlank
    @Size(max = 50)
    private String name;

    @NotNull
    @NotBlank
    @Size(max = 25)
    private String city;

    @Lob
    private byte[] image;

    public Club(final UUID id, final String name, final String city, final byte[] image) {
        this.id = Objects.requireNonNullElseGet(id, UUID::randomUUID);
        this.name = name;
        this.city = city;
        this.image = image;
    }
}
