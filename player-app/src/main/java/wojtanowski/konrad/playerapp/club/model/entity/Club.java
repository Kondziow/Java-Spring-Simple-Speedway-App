package wojtanowski.konrad.playerapp.club.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import wojtanowski.konrad.playerapp.player.model.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Club {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "club_id", updatable = false, nullable = false)
    private UUID id;

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Player> players = new ArrayList<>();
}
