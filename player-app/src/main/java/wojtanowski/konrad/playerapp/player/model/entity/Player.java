package wojtanowski.konrad.playerapp.player.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import wojtanowski.konrad.playerapp.club.model.entity.Club;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Builder
//@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Player {
    @Id
    @NotNull
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    @NotBlank
    @Size(max = 50)
    private String name;

    @NotNull
    @NotBlank
    @Size(max = 50)
    private String surname;

    private LocalDate birthDate;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "club_id")
    private Club club;

    @Lob
    private byte[] image;

    public Player(final UUID id, final String name, final String surname, final LocalDate birthDate, final Club club, final byte[] image) {
        this.id = Objects.requireNonNullElseGet(id, UUID::randomUUID);
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.club = club;
        this.image = image;
    }
}
