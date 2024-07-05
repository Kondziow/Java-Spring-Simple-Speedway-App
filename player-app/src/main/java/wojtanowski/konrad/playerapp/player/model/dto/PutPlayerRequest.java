package wojtanowski.konrad.playerapp.player.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import wojtanowski.konrad.playerapp.club.model.model.GetClubResponse;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PutPlayerRequest {
    @NotNull
    @NotBlank
    @Size(max = 50)
    private String name;

    @NotNull
    @NotBlank
    @Size(max = 50)
    private String surname;

    private LocalDate birthDate;

    private GetClubResponse club;
}
