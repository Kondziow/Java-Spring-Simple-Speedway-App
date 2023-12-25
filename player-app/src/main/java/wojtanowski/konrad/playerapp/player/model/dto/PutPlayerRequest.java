package wojtanowski.konrad.playerapp.player.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

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

    private Date birthDate;
}
